package leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 *
 *  Example:
 *
 * Given nums = [2, 7, 11, 15], target = 9,
 *
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 *
 */
public class _001_TwoSum_2 {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> cache = new HashMap<Integer, Integer>();
        int l = nums.length;
        for (int i=0; i<l; i++) {
            int d = target - nums[i];
            Integer find = cache.get(d);
            if (find != null) {
                return new int[]{find, i};
            } else {
                cache.put(nums[i], i);
            }
        }
        return new int[]{-1, -1};
    }

    public static void main(String[] args) {
        _001_TwoSum_2 twoSum_1 = new _001_TwoSum_2();
        int[] nums = new int[]{3,6,1,2,5, 9,7};
        int target = 11;
        int[] result = twoSum_1.twoSum(nums, target);

        System.out.println(Arrays.toString(result));
    }
}
