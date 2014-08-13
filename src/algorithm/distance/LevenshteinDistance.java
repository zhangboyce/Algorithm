package algorithm.distance;


import common.utils.SegmenterUtils;
import common.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boyce on 2014/6/30.
 */
public class LevenshteinDistance {

    public static void main(String[] args) {
        String text1 = "你妈妈喊你回家吃饭";
        String text2 = "你爸爸正在叫你回家吃饭";

        LevenshteinDistance levenshteinDistance = new LevenshteinDistance(text1, text2);

        double similarity1 = levenshteinDistance.getSimilarityByChar();
        double similarity2 = levenshteinDistance.getSimilarityByWords();
        System.out.println("text1 and text2 similarity by char: " + similarity1);
        System.out.println("text1 and text2 similarity by words: " + similarity2);
    }

    private String text1;
    private String text2;

    public LevenshteinDistance(String text1, String text2) {
        this.text1 = text1;
        this.text2 = text2;
    }

    public double getSimilarityByChar() {

        char[] chars1 = text1.toCharArray();
        char[] chars2 = text2.toCharArray();

        String[] strings1 = new String[chars1.length];
        String[] strings2 = new String[chars2.length];

        for (int i=0; i<chars1.length; i++) {
            strings1[i] = chars1[i] + "";
        }

        for (int i=0; i<chars2.length; i++) {
            strings2[i] = chars2[i] + "";
        }

        int val = levenshteinDistance(strings1, strings2);
        return 1 - (double)val / Math.max(text1.length(), text2.length());
    }

    public double getSimilarityByWords() {

        List<String> strings1 = SegmenterUtils.IKSEG.segment(text1);
        List<String> strings2 = SegmenterUtils.IKSEG.segment(text2);

        String[] array1 = new String[strings1.size()];
        String[] array2 = new String[strings2.size()];

        for (int i=0; i<strings1.size(); i++) {
            array1[i] = strings1.get(i);
        }

        for (int i=0; i<strings2.size(); i++) {
            array2[i] = strings2.get(i);
        }

        int val = levenshteinDistance(array1, array2);
        return 1 - (double)val / Math.max(text1.length(), text2.length());
    }

    private int levenshteinDistance(String[] words1, String[] words2) {

        int n = words1.length;
        int m = words2.length;

        if (0 == n || 0 == m)
            return 0;

        int[][] matrix = new int[n+1][m+1];
        for (int i=0; i<n+1; i++) {
            matrix[i][0] = i;
        }
        for (int j=0; j<m+1; j++) {
            matrix[0][j] = j;
        }

        int temp = 0;
        List<Integer> comparison;

        for (int i=1; i<n+1; i++) {
            for (int j=1; j<m+1; j++) {
                if (StringUtils.isEquals(words1[i - 1], words2[j - 1]))
                    temp = 0;
                else
                    temp = 1;

                comparison = new ArrayList<Integer>();
                comparison.add(matrix[i-1][j]+1);
                comparison.add(matrix[i][j-1]+1);
                comparison.add(matrix[i-1][j-1] + temp);

                // 对相邻字符交换位置的处理判断
                if (i>1 && j>1) {
                    if (StringUtils.isEquals(words1[i-1], words2[j-2]) && StringUtils.isEquals(words1[i-2], words2[j-1]))
                        comparison.add(matrix[i-2][j-2]+1);
                }

                matrix[i][j] = lower(comparison);
            }
        }

        for (int i = 0; i <= n; i++)  {
            for (int j = 0; j <= m; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println("");
        }
        return matrix[n][m];

    }

    private int lower(List<Integer> list) {
        int lower = list.get(1);
        for (int i=0; i<list.size(); i++) {
            int a = list.get(i);
            lower = Math.min(a, lower);
        }

        return lower;
    }
}
