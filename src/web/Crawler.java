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
        String baseUrl = "https://freesound.org/people/AKUSTIKA/sounds/?page=";
        for (int i=1; i<12; i++) {
            String url = baseUrl + i;
            System.out.println(i + ": " + url);
            task = new CrawlerTask(url);
            String body = task.body();

            handle(body);
        }
    }

    public static void handle(String body) {
        Document document = asJsoup(body);
//        System.out.println(body);
        Elements elements = document.select(".sample_player_small");
        System.out.println(elements.size());
        for (Element element: elements) {
            String id = element.attr("id");

            Elements a = element.select(".sound_filename").select("a.title");
            String href = a.attr("href");
            String title = a.attr("title");
            System.out.println( "https://freesound.org" + href + "" + "download/" + id +"__akustika__" + title);
        }
    }

    public static Document asJsoup(String text) {
        if (StringUtils.isEmpty(text)) return null;

        Document doc = Jsoup.parse(text);
        return doc;
    }
}
