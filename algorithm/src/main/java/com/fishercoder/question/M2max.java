package com.fishercoder.question;

/**
 * 打水漂，最大价值
 * 求数组中连续子数组之和最大，最少两个数
 */
public class M2max {
    public static void main(String[] args) {
        int[] nums = {-2, 11, -4, 13, -5, -2};
        int[] nums1 = {5, -2, -4, 7, -9};
        System.out.println(new Solution().maxSubArray(nums1));
    }

    /**
     * 暴力法
     */
    public static class Solution {
        public int maxSubArray(int[] nums) {
            int ans = 0;
            int n = nums.length;
            for (int i = 0; i < n - 1; i++) {
                int max = 0;
                int prev = nums[i];
                for (int j = i + 1; j < n; j++) {
                    prev += nums[j];
                    max = Math.max(prev, max);
                }
                ans = Math.max(ans, max);
            }
            return ans;
        }
    }

    /**
     * 没考虑最少两个数
     */
    public static class Solution1 {
        public int maxSubArray(int[] nums) {
            int start = 0;
            int end = 0;
            int temp = 1;
            int ans = Integer.MIN_VALUE;
            int sum = 0;
            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];
                if (sum < 0) {
                    sum = 0;
                    temp = i + 1;
                }
                if (sum > ans) {
                    ans = sum;
                    start = temp;
                    end = i;
                }
            }
            return ans;
        }
    }
}
