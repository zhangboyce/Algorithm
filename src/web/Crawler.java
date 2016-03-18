package web;

import common.utils.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * User: Boyce
 * Date: 16/3/12
 * Time: 16:16
 */
public class Crawler {
    public static void main(String[] args) throws Exception {
        CrawlerTask task;
        String baseUrl = "http://cl.to1024.club/thread0806.php?fid=4&search=&page=";
        for (int i=1; i<2; i++) {
            String url = baseUrl + i;
            task = new CrawlerTask(url);
            String body = task.body();

            handle(body);
        }
    }

    public static void handle(String body) {
        Document document = asJsoup(body);
        Elements elements = document.select("#ajaxtable").select("tr.tr3")
                .select("td").get(1).select("a");;
        for (Element element: elements) {
            String name = element.text();
            if (name.contains("剧情片") || name.contains("中文字幕")) {
                System.out.println("http://cl.to1024.club/" + element.attr("href"));
                System.out.println(name);
            }
        }
    }

    public static Document asJsoup(String text) {
        if (StringUtils.isEmpty(text)) return null;

        Document doc = Jsoup.parse(text);
        return doc;
    }
}
