package common.utils;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.http.*;
import org.apache.http.client.entity.DeflateDecompressingEntity;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.Proxy;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * User: Boyce
 * Date: 16/3/12
 * Time: 17:34
 */
public class HttpUtils {
    private static final int CONN_TIMEOUT = 5000; // 5s
    private static final int READ_TIMEOUT = 30000; // 30s

    private static TrustManager[] TRUST_ALL = new TrustManager[]{new X509TrustManager() {
        public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws CertificateException {

        }

        public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws CertificateException {
        }

        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }};

    private static Scheme HTTPS = null;

    static {
        try {
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, TRUST_ALL, null);
            SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HTTPS = new Scheme("https", sf, 443);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static final HttpResponseInterceptor RESPONSE_INTERCEPTOR = new HttpResponseInterceptor() {
        public void process(final HttpResponse response, final HttpContext context) throws HttpException, IOException {
            if (response != null && response.getEntity() != null) {
                Header encoding = response.getEntity().getContentEncoding();
                if (encoding == null) return;
                for (HeaderElement element : encoding.getElements()) {
                    String name = element.getName();
                    if ("gzip".equalsIgnoreCase(name)) {
                        response.setEntity(new GzipDecompressingEntity(response.getEntity()));
                    } else if ("deflate".equalsIgnoreCase(name)) {
                        response.setEntity(new DeflateDecompressingEntity(response.getEntity()));
                    }
                }
            }
        }
    };

    private static final DefaultRedirectStrategy REDIRECT_STRATEGY = new DefaultRedirectStrategy() {
        public boolean isRedirected(HttpRequest request, HttpResponse response, HttpContext context) {
            boolean isRedirect = false;
            try {
                isRedirect = super.isRedirected(request, response, context);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            if (!isRedirect) {
                int responseCode = response.getStatusLine().getStatusCode();
                if (responseCode == 301 || responseCode == 302) {
                    return true;
                }
            }
            return isRedirect;
        }
    };


    public static HttpResponse request(HttpUriRequest request) throws IOException {
        return request(request, null);
    }

    public static HttpResponse request(HttpUriRequest request, Proxy proxy) throws IOException {
        return request(request, proxy, null);
    }

    public static HttpResponse request(HttpUriRequest request, Proxy proxy, Integer readTimeout) throws IOException {
        return request(request, proxy, readTimeout, null);
    }

    /**
     * 请求并返回结果
     *
     * @param request
     * @param proxy
     * @param readTimeout
     * @param connTimeout
     * @return
     * @throws Throwable
     */
    public static HttpResponse request(HttpUriRequest request, Proxy proxy, Integer readTimeout, Integer connTimeout) throws IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();

        HttpParams params = httpClient.getParams();

        params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connTimeout != null ? connTimeout : CONN_TIMEOUT);
        params.setParameter(CoreConnectionPNames.SO_TIMEOUT, readTimeout != null ? readTimeout : READ_TIMEOUT);

        // bug fix https://code.google.com/p/crawler4j/issues/detail?id=136
        params.setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
        params.setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);

        if (proxy != null) {
            params.setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(proxy.host, proxy.port, "http"));
        }

        httpClient.addResponseInterceptor(RESPONSE_INTERCEPTOR);
        httpClient.setRedirectStrategy(REDIRECT_STRATEGY);
        httpClient.setCookieStore(new BasicCookieStore());

        //        httpClient.getConnectionManager().getSchemeRegistry().register(HTTPS);

        return httpClient.execute(request);
    }


    public static HttpUriRequest buildRequest(String method, String url) {
        return buildRequest(method, url, null);
    }

    public static HttpUriRequest buildRequest(String method, String url, Map<String, Object> params) {
        return buildRequest(method, url, params, null);
    }

    public static HttpUriRequest buildRequest(String method, String url, Map<String, Object> params, Map<String, Object> headers) {
        return buildRequest(method, url, params, headers, null);
    }

    /**
     * @param method
     * @param url
     * @param headers
     * @param params
     * @param charset
     * @return
     */
    public static HttpUriRequest buildRequest(String method, String url, Map<String, Object> params, Map<String, Object> headers, String charset) {
        HttpUriRequest request = null;

        if ("POST".equalsIgnoreCase(method)) {
            HttpPost httpPost = new HttpPost(url);

            if (params != null) {
                List<NameValuePair> pairs = new ArrayList();
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    pairs.add(new BasicNameValuePair(entry.getKey(), Objects.toString(entry.getValue(), "")));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, Charset.forName(charset != null ? charset : "UTF-8"));
                httpPost.setEntity(entity);
            }
            request = httpPost;
        } else {
            try {
                URIBuilder builder = new URIBuilder(url);
                if (params != null) {
                    for (Map.Entry<String, Object> entry : params.entrySet()) {
                        builder.setParameter(entry.getKey(), entry.getValue().toString());
                    }
                }
                request = new HttpGet(builder.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // headers
        if (request != null && headers != null) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                request.setHeader(entry.getKey(), entry.getValue().toString());
            }
        }

        return request;
    }

    static String buildUrl(String url) {
        return buildUrl(url, null);
    }

    static String buildUrl(String url, Map<String, Object> params) {
        try {
            URIBuilder builder = new URIBuilder(url);
            if (params != null) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    if (entry != null && entry.getKey() != null && entry.getValue() != null)
                        builder.setParameter(entry.getKey(), entry.getValue().toString());
                }
            }
            return builder.build().toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class Proxy {
        String host; // ip/hostname
        int port;
        /**
         * 解析文本生成代理对象
         * @param proxy 文本格式为：host:port
         * @return 代理对象
         */
        public static Proxy fromShort(String proxy) {
            int index = proxy == null ? -1 : proxy.indexOf(":");
            if (index < 0 || index == proxy.length() - 1) return null;
            Proxy p = new Proxy();
            p.host = proxy.substring(0, index);
            p.port = NumberUtils.toInt(proxy.substring(index + 1));

            return p;
        }

        public String toShort() {
            return host + ":" + port;
        }

        public static boolean isValid(String proxy){
            if (StringUtils.isEmpty(proxy)) return false;
            return proxy.matches("^\\d+\\.\\d+\\.\\d+\\.\\d+\\:\\d+$");
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Proxy proxy = (Proxy) o;
            return Objects.equals(port, proxy.port) &&
                    Objects.equals(host, proxy.host);
        }

        @Override
        public int hashCode() {
            return Objects.hash(host, port);
        }

        public String toString() {
            return toShort();
        }

        public static void main(String[] args) {
            System.out.println(isValid("132.13.1.1:9090"));
            System.out.println(isValid("132.13.1.1:9090 "));
            System.out.println(isValid("132.1.3.1.1:9090"));
            System.out.println(isValid("132.13..1:9090"));
            System.out.println(isValid("132.13.1.1:8"));
            System.out.println(isValid("132.13.1.1 9090"));
        }
    }
}
