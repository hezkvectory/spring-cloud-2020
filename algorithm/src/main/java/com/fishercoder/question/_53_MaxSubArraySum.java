package com.fishercoder.question;

public class _53_MaxSubArraySum {
    public static class Solution {
        public int maxSum(int[] nums) {
            int len = nums.length;

            int min = Integer.MAX_VALUE;

            int preSum = 0;
            int ans = Integer.MIN_VALUE;
            for (int i = 0; i < len; i++) {
                preSum += nums[i];
                min = Math.min(min, preSum);
                if (i > 0) {
                    ans = Math.max(ans, preSum - min);
                }
            }
            return ans == Integer.MIN_VALUE ? -1 : ans;
        }
    }

    /**
     * me
     */
    public static class Solution2 {
        public int maxSum(int[] nums) {
            int len = nums.length;
            int[] dp = new int[len];
            dp[0] = nums[0];
            int ans = Integer.MIN_VALUE;
            boolean single = false;
            for (int i = 1; i < len; i++) {
                if (i == 1) {
                    if (dp[i - 1] > 0) {
                        if (dp[i - 1] + nums[i] > nums[i]) {
                            dp[i] = dp[i - 1] + nums[i];
                            ans = Math.max(ans, dp[i]);
                        } else {
                            dp[i] = nums[i];
                            single = true;
                        }
                    } else {
                        dp[i] = nums[i];
                        single = true;
                    }
                } else {
                    if (single) {
                        if (dp[i - 1] > 0) {
                            if (dp[i - 1] + nums[i] > nums[i]) {
                                dp[i] = dp[i - 1] + nums[i];
                                ans = Math.max(ans, dp[i]);
                                single = false;
                            } else {
                                dp[i] = nums[i];
                                single = true;
                            }
                        } else {
                            dp[i] = nums[i];
                            single = true;
                        }
                    } else {
                        if (dp[i - 1] > 0) {
                            if (dp[i - 1] + nums[i] > nums[i]) {
                                dp[i] = dp[i - 1] + nums[i];
                                ans = Math.max(ans, dp[i]);
                                single = false;
                            } else {
                                dp[i] = nums[i];
                                single = true;
                            }
                        } else {
                            dp[i] = nums[i];
                            single = true;
                        }
                    }
                }
            }
            return ans;
        }
    }

    public static class Solution4 {
        public int maxSum(int[] nums) {
            int len = nums.length;
            if (len <= 1) {
                return -1;
            }
            int[] dp = new int[len];
            dp[0] = nums[0];
            int ans = Integer.MIN_VALUE;
            for (int i = 1; i < len; i++) {
//                if (dp[i - 1] > 0 && dp[i - 1] + nums[i] > nums[i]) {
                if (dp[i - 1] > 0) {
                    dp[i] = dp[i - 1] + nums[i];
                    ans = Math.max(ans, dp[i]);
                } else {
                    dp[i] = nums[i];
                }
            }
            return ans == Integer.MIN_VALUE ? -1 : ans;
        }
    }

    public static class Solution3 {
        public int maxSum(int[] array) {
            int len = array.length;
            if (len == 1) {   //增加判断  是否为一个元素 并直接输出
                System.out.println("only one elemnet and Max is:" + array[0]);
                return 0;
            } else {
                int max = -1000;//用来记录数组c[]中的最大值
                int start = 0;//记录数组中子数组的最大和的开始位置
                int end = 0;//记录数组中子数组的最大和的结束位置
                int tmp = 0;//中间变量

                int[] c = new int[len];//引入一个数组
                c[0] = array[0];
                for (int i = 1; i < len; i++) {
                    if (c[i - 1] > 0) {
                        c[i] = c[i - 1] + array[i];
                    } else {
                        c[i] = array[i];
                        tmp = i;
                    }
                    if (c[i] > max && c[i] != array[i]) {
                        max = c[i];
                        start = tmp;
                        end = i;
                    }
                }
                System.out.println(start + "~" + end + "Max is:" + max);
                return max;
            }
        }
    }

    public static void main(String[] args) {
//        int[] nums = {3, -2, 4, 1, -7};
//        int[] nums = {-9, 1, 3, 5, -1, -5, 7, 3, 1};
//        int[] nums = {-9, 1, 3, 5, -1, 7, -5, 3, 1};
//        int[] nums = {1, -2, 4, 8, -4, 7, -1, -5};
//        int[] nums = {-1, 3, 9, 0, -9, 3, 5, -6};
//        int [] nums= {-2};
        int[] nums = {-1, -3, 9, 0, -9, 3, 5, -6};

        System.out.println(new Solution2().maxSum(nums));
        System.out.println(new Solution4().maxSum(nums));
        System.out.println(new Solution3().maxSum(nums));
    }
}
