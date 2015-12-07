package bayesain.example;

import bayesain.naive.Classification;
import bayesain.naive.Classifications;
import bayesain.naive.Item;
import bayesain.naive.XVector;
import common.utils.FileUtils;
import common.utils.SegmenterUtils;

import java.io.File;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Boyce
 * Date: 6/12/15
 * Time: 23:43
 * To change this template use File | Settings | File Templates.
 */
public class CommentClassification {

    private final static String BASE_PATH = "/Users/Boyce/GitProjects/Algorithm/src/bayesain/example/";

    public static void main(String[] args) {
        CommentSegmentor.loadDic();

        Classifications cs = new Classifications();

        File jd_12_345_comment_count = new File(BASE_PATH + "data/jd_12_345_comment_count.txt");
        List<String> lines = FileUtils.readLines(jd_12_345_comment_count);

        String jd_12 = lines.get(0);
        String jd_345 = lines.get(1);

        Classification c1 = new Classification("jd_12", Integer.valueOf(jd_12), cs);
        Classification c2 = new Classification("jd_345", Integer.valueOf(jd_345), cs);

        File jd_12_score_comment_word_count = new File(BASE_PATH + "data/jd_12_score_comment_word_count.txt");
        List<String> jd_12_count = FileUtils.readLines(jd_12_score_comment_word_count);

        for(String line: jd_12_count) {
            if (null != line) {
                String[] values = line.split(":");
                Item i = Item.getInstance(values[0]);
                i.putCount(c1, Integer.valueOf(values[1]));
            }
        }

        File jd_345_score_comment_word_count = new File(BASE_PATH + "data/jd_345_score_comment_word_count.txt");
        List<String> jd_345_count = FileUtils.readLines(jd_345_score_comment_word_count);

        for(String line: jd_345_count) {
            if (null != line) {
                String[] values = line.split(":");
                Item i = Item.getInstance(values[0]);
                i.putCount(c2, Integer.valueOf(values[1]));
            }
        }


        test_jd_12(cs);
//        test_jd_345(cs);

    }

    private static void test_jd_12(Classifications cs) {

        File test_12_comment = new File(BASE_PATH + "data/jd_test_12_comment.txt");
        List<String> test_12_lines = FileUtils.readLines(test_12_comment);
        int test_12_count = test_12_lines.size();

        int errorCount = 0;
        int cannotClassify = 0;
        for (String comment: test_12_lines) {
            List<String> words = SegmenterUtils.IKSEG.segment(comment);

            XVector vector = new XVector();
            for (String word: words) {
                vector.addItem(Item.getInstance(word));
            }
            Classification c = cs.classify(vector);
            if (null == c) {
                System.out.println("cannot classify: " + comment);
                cannotClassify ++;
                continue;
            }
            if (c.getName().equals("jd_345")) {
                System.out.println("jd_345: error> " + comment);
                errorCount ++;
            }
        }

        System.out.println("jd_12 error ratio: ");
        System.out.println(errorCount+ "/" + test_12_count +": " + (double)errorCount/test_12_count);

        System.out.println("jd_12 cannot classify: ");
        System.out.println(cannotClassify+ "/" + test_12_count +": " + (double)cannotClassify/test_12_count);
    }

    private static void test_jd_345(Classifications cs) {

        File test_345_comment = new File(BASE_PATH + "data/jd_test_345_comment.txt");
        List<String> test_345_lines = FileUtils.readLines(test_345_comment);
        int test_345_count = test_345_lines.size();

        int errorCount = 0;
        int cannotClassify = 0;
        for (String comment: test_345_lines) {
            List<String> words = SegmenterUtils.IKSEG.segment(comment);

            XVector vector = new XVector();
            for (String word: words) {
                vector.addItem(Item.getInstance(word));
            }
            Classification c = cs.classify(vector);
            if (null == c) {
                System.out.println("cannot classify: " + comment);
                cannotClassify ++;
                continue;
            }

            if (c.getName().equals("jd_12")) {
                System.out.println("jd_12: error> " + comment);
                errorCount ++;
            }
        }

        System.out.println("jd_345 error ratio: ");
        System.out.println(errorCount+ "/" + test_345_count +": " + (double)errorCount/test_345_count);

        System.out.println("jd_345 cannot classify: ");
        System.out.println(cannotClassify+ "/" + test_345_count +": " + (double)cannotClassify/test_345_count);
    }
}
