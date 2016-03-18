package web;

import common.utils.HttpUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

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
        return body("UTF-8");
    }

    public String body(String charset) throws Exception {
//        HttpUriRequest httpRequest = HttpUtils.buildRequest("GET", url);
//        HttpResponse httpResponse = HttpUtils.request(httpRequest);
        // 定义HttpClient
        HttpClient client = new DefaultHttpClient();
        // 实例化HTTP方法
        HttpGet request = new HttpGet();
        request.setURI(new URI(url));
        HttpResponse response = client.execute(request);

        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity, charset);

        return body;
    }

}
