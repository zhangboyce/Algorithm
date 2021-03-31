package web;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.net.URI;

/**
 * User: Boyce
 * Date: 16/3/12
 * Time: 17:41
 */
public class CrawlerTask {
    private String url;

    public CrawlerTask(String url) {
        this.url = url;
    }

    public String body() throws Exception {
        return body("gbk");
    }

    public String body(String charset) throws Exception {
        HttpClient client = new DefaultHttpClient();
        HttpHost proxy = new HttpHost("49.70.89.70", 9999, null);
        client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
        HttpGet request = new HttpGet();
        request.setURI(new URI(url));
        HttpResponse response = client.execute(request);

        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity, charset);

        return body;
    }

}
