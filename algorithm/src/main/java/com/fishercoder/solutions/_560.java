package com.fishercoder.solutions;

import java.util.HashMap;
import java.util.Map;

/**
 * 560. Subarray Sum Equals K
 * <p>
 * Given an array of integers and an integer k, you need to find the total number of continuous subarrays whose sum equals to k.
 * <p>
 * Example 1:
 * Input:nums = [1,1,1], k = 2
 * Output: 2
 * Note:
 * The length of the array is in range [1, 20,000].
 * The range of numbers in the array is [-1000, 1000] and the range of the integer k is [-1e7, 1e7].
 */
public class _560 {

    public static class Solution1 {

        /**
         * credit: https://discuss.leetcode.com/topic/87850/java-solution-presum-hashmap
         * We know the key to solve this problem is SUM[i, j].
         * So if we know SUM[0, i - 1] and SUM[0, j],
         * then we can easily get SUM[i, j] via (SUM[0, j] - SUM[0, i-1]).
         * To achieve this, we just need to go through the array,
         * calculate the current sum and save number of all seen PreSum to a HashMap.
         * <p>
         * Time complexity O(n), Space complexity O(n).
         */
        public int subarraySum(int[] nums, int k) {
            Map<Integer, Integer> preSum = new HashMap();
            int sum = 0;
            int result = 0;
            preSum.put(0, 1);
            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];
                if (preSum.containsKey(sum - k)) {
                    result += preSum.get(sum - k);
                }
                preSum.put(sum, preSum.getOrDefault(sum, 0) + 1);
            }
            return result;
        }
    }


    //滑动窗口解法还未debug正确
    public static class Solution {
        public int subarraySum(int[] nums, int k) {
            int len = nums.length;
            int left = 0, right = 0;
            int sum = 0;
            int ans = 0;
            while (right < len) {
                sum += nums[right];
                while (sum >= k && left < len) {
                    if (sum == k) {
                        ans++;
                    }
                    sum -= nums[left];
                    left++;
                }
                right++;
            }
            return ans;
        }
    }

}
