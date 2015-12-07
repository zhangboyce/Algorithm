package bayesain.example;

import common.utils.FileUtils;
import common.utils.SegmenterUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Boyce
 * Date: 6/12/15
 * Time: 22:19
 * To change this template use File | Settings | File Templates.
 */
public class CommentSegmentor {

    private final static String BASE_PATH = "/Users/Boyce/GitProjects/Algorithm/src/bayesain/example/";

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

        loadDic();

        File jd_12_score_comment = new File(BASE_PATH + "data/jd_12_score_comment.txt");
        File jd_12_score_comment_word_count = new File(BASE_PATH + "data/jd_12_score_comment_word_count.txt");

        File jd_345_score_comment = new File(BASE_PATH + "data/jd_345_score_comment.txt");
        File jd_345_score_comment_word_count = new File(BASE_PATH + "data/jd_345_score_comment_word_count.txt");

        int jd_12_count = countWord(jd_12_score_comment, jd_12_score_comment_word_count);
        int jd_345_count = countWord(jd_345_score_comment, jd_345_score_comment_word_count);

        PrintWriter writer = new PrintWriter(BASE_PATH + "data/jd_12_345_comment_count.txt", "UTF-8");
        writer.println(jd_12_count);
        writer.println(jd_345_count);
        writer.close();

    }

    private static int countWord(File read, File write) throws FileNotFoundException, UnsupportedEncodingException {
        List<String> jd_12_lines = FileUtils.readLines(read);

        Map<String, Integer> word_count = new HashMap<String, Integer>();
        for (String line: jd_12_lines) {
            List<String> words = SegmenterUtils.IKSEG.segment(line);

            // 去重，一条评论一个词只统计一次
            List<String> listWithoutDup = new ArrayList<String>(new HashSet<String>(words));
            for (String word: listWithoutDup) {
                Integer count = word_count.get(word);
                if (count == null) count = 0;
                word_count.put(word, ++count);
            }
        }

        // sort map
        List<Map.Entry<String, Integer>> entries = new ArrayList(word_count.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>(){
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2){
                return e2.getValue() - e1.getValue();
            }
        });

        PrintWriter writer = new PrintWriter(write, "UTF-8");
        for (Map.Entry entry: entries) {
            writer.println(entry.getKey() + ":" + entry.getValue());
        }
        System.out.println(entries);
        writer.close();

        return jd_12_lines.size();
    }

    public static void loadDic() {
        File f_keywords = new File(BASE_PATH + "data/dic/keywords.dic");
        File f_stopwords = new File(BASE_PATH + "data/dic/stopwords.dic");

        List<String> keywords = FileUtils.readLines(f_keywords);
        List<String> stopwords = FileUtils.readLines(f_stopwords);

        SegmenterUtils.IKSEG.addDicWords(keywords);
        SegmenterUtils.IKSEG.addStopWords(stopwords);

        System.out.println("add keywords: " + keywords);
        System.out.println("add stopwords: " + stopwords);
    }
}
