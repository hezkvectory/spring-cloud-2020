package com.fishercoder.solutions;

/**
 * 209. Minimum Size Subarray Sum
 * <p>
 * Given an array of n positive integers and a positive integer s,
 * find the minimal length of a contiguous subarray of which the sum ≥ s.
 * If there isn't one, return 0 instead.
 * <p>
 * For example, given the array [2,3,1,2,4,3] and s = 7,
 * the subarray [4,3] has the minimal length under the problem constraint.
 * <p>
 * click to show more practice.
 * <p>
 * More practice:
 * If you have figured out the O(n) solution,
 * try coding another solution of which the time complexity is O(n log n).
 */
public class _209 {

    public static class Solution1 {
        public int minSubArrayLen(int s, int[] nums) {
            if (nums == null || nums.length == 0) {
                return 0;
            }
            int i = 0;
            int j = 0;
            int min = Integer.MAX_VALUE;
            int sum = 0;
            while (j < nums.length) {
                sum += nums[j++];

                while (sum >= s) {
                    min = Math.min(min, j - i);
                    sum -= nums[i++];
                }
            }
            return min == Integer.MAX_VALUE ? 0 : min;
        }
    }


    public static class Solution {

        public int minLen(int[] nums, int target) {
            int len = nums.length;
            int sum = 0;
            int left = 0, right = 0;
            int result = Integer.MAX_VALUE;
            int minLeft = 0;

            while (right < len) {
                sum += nums[right];

                while (sum >= target) {
                    if (sum == target) {
                        result = Math.min(result, right - left + 1);
                        minLeft = left;
                    }
                    sum -= nums[left];
                    left++;
                }
                right++;
            }
            System.out.println("minLeft:" + minLeft);
            return result == Integer.MAX_VALUE ?  0 : result;
        }
    }

    public static void main(String[] args) {
//        int[] nums = {2, 3, 1, 2, 4, 3};
        int[] nums = {1, 1, 1, 1, 1, 1};
        int target = 7;
        System.out.println(new Solution().minLen(nums, target));
    }


}
