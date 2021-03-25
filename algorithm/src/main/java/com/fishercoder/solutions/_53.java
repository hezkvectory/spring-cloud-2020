package com.fishercoder.solutions;

/**
 * 53. Maximum Subarray
 * Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.
 * <p>
 * Example:
 * Input: [-2,1,-3,4,-1,2,1,-5,4],
 * Output: 6
 * Explanation: [4,-1,2,1] has the largest sum = 6.
 * <p>
 * Follow up:
 * If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach, which is more subtle.
 */

public class _53 {


    static class Solution {
        public int missingNumber(int[] arr) {
            int i = 0, j = arr.length - 1;
            while (i <= j) {
                int m = (i + j) / 2;
                if (arr[m] == m) {
                    i = m + 1;
                } else {
                    j = m - 1;
                }
            }
            return i;
        }
    }

    public static class Solution1 {
        /**
         * https://leetcode.com/problems/maximum-subarray/discuss/20211/accepted-on-solution-in-java
         */
        public int maxSubArray(int[] nums) {
            int maxSoFar = nums[0];
            int maxEndingHere = nums[0];
            for (int i = 1; i < nums.length; i++) {
                maxEndingHere = Math.max(nums[i], maxEndingHere + nums[i]);
                maxSoFar = Math.max(maxEndingHere, maxSoFar);
            }
            return maxSoFar;
        }
    }
}
