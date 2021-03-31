package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given a string, find the length of the longest substring without repeating characters.
 *
 * Example 1:
 *
 * Input: "abcabcbb"
 * Output: 3
 * Explanation: The answer is "abc", with the length of 3.
 */
public class _003_LongestSubstring {
    public int lengthOfLongestSubstring(String s) {
        return __lengthOfLongestSubstring__(s).length();
    }

    private static String __lengthOfLongestSubstring__(String s) {
        if (s == null || "".equals(s)) return s;

        char[] chars = s.toCharArray();
        int l = 0;
        int si = 0, ej = 0;

        List<Character> results = new ArrayList<>();
        outer:
        for (int i=0; i<chars.length; i++) {
            results.add(chars[i]);
            for (int j=i+1; j< chars.length; j++) {
                if (results.contains(chars[j])) {
                    if (j - i > l) {
                        si = i;
                        ej = j-1;
                        l = j - i;
                    }
                    i = j-1;
                    results.clear();
                    continue outer;
                } else {
                    results.add(chars[j]);
                }
            }
        }

        char[] rc = Arrays.copyOfRange(chars, si, ej+1);
        return String.valueOf(rc);
    }

    public static void main(String[] args) {
        String s = "uo";
        String l = __lengthOfLongestSubstring__(s);

        System.out.println(l);
    }
}
