package algorithm.distance;


import common.utils.SegmenterUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by boyce on 2014/6/30.
 * 余弦相似度算法
 */
public class CosineSimilarity {

    public static void main(String[] args) {
        String text1 = "你妈妈喊你回家吃饭";
        String text2 = "你爸爸正在叫你回家吃饭";
        CosineSimilarity cosineSimilarity = new CosineSimilarity(text1, text2);

        try {
           double similarity = cosineSimilarity.calculateSimilarity();
            System.out.println("text1 and text2 similarity: " + similarity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CosineSimilarity(String text1, String text2) {
        this.text1 = text1;
        this.text2 = text2;
    }

    private String text1;
    private String text2;

    public double calculateSimilarity() throws IOException {

        List<String> text1Words = SegmenterUtils.IKSEG.segment(text1);
        List<String> text2Words = SegmenterUtils.IKSEG.segment(text2);

        if (text1Words.isEmpty() || text2.isEmpty())
            return 0.0;

        Map<String, int[]> algorithmMap = new HashMap<String, int[]>();
        for (String word: text1Words) {
            int[] fq = algorithmMap.get(word);
            if (fq != null)
                fq[0] ++;
            else {
                fq = new int[2];
                fq[0] = 1;
                fq[1] = 0;
                algorithmMap.put(word, fq);
            }
        }

        for (String word: text2Words) {
            int[] fq = algorithmMap.get(word);
            if (fq != null)
                fq[1] ++;
            else {
                fq = new int[2];
                fq[1] = 1;
                fq[0] = 0;
                algorithmMap.put(word, fq);
            }
        }

        double sqdoc1 = 0;
        double sqdoc2 = 0;
        double denominator = 0;
        for(String key: algorithmMap.keySet()) {
            int[] c = algorithmMap.get(key);
            denominator += c[0]*c[1];
            sqdoc1 += c[0]*c[0];
            sqdoc2 += c[1]*c[1];
        }
        return denominator / Math.sqrt(sqdoc1*sqdoc2);
    }
}
