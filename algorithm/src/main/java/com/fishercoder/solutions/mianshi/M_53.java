package com.fishercoder.solutions.mianshi;

/**
 * 连续数列最大和
 */
public class M_53 {

    public static void main(String[] args) {
        int[] nums = {-10, -9, -8, -7, -6, -5, -4, -3, -2, 2,-1};
        Solution3 solution = new Solution3();
        System.out.println(solution.maxSubArray(nums));
    }

    static class Solution3 {
        public int maxSubArray(int[] nums) {
            int ans = nums[0];
            int sum = 0;
            for(int num: nums) {
                if(sum > 0) {
                    sum += num;
                } else {
                    sum = num;
                }
                ans = Math.max(ans, sum);
            }
            return ans;
        }
    }

    //贪心算法
    public static class Solution {
        public int maxSubArray(int[] nums) {
            int result = Integer.MIN_VALUE;
            int count = 0;
            for (int i = 0; i < nums.length; i++) {
                count += nums[i];
                if (count > result) {
                    result = count;
                }
                if (count < 0) {
                    count = 0;
                }
            }
            return result;
        }
    }

    public static class Solution2 {
        public int maxSubArray(int[] nums) {
            int[] dp = new int[nums.length + 1];
            dp[0] = nums[0];
            int result = dp[0];
            for (int i = 1; i < nums.length; i++) {
                dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
                if (result < dp[i]) {
                    result = dp[i];
                }
            }
            return result;
        }
    }
}
