package com.hezk.algorithm;

/**
 * 贪心算法
 */
public class JumpGame {

    /**
     * 跳跃游戏
     */
    public static class Solution {
        public boolean jump(int[] nums) {
            int len = nums.length;
            int most = 0;
            for (int i = 0; i < len - 1; i++) {
                most = Math.max(most, i + nums[i]);
                if (most >= len - 1) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 跳跃游戏2
     * 最少跳跃次数
     * 正向查找
     */
    public static class Solution1 {
        public int jump(int[] nums) {
            int end = 0;
            int steps = 0;
            int position = 0;
            int len = nums.length;
            for (int i = 0; i < len - 1; i++) {
                position = Math.max(position, i + nums[i]);
                if (i == end) {
                    end = position;
                    steps++;
                }
            }
            return steps;
        }
    }

    /**
     * 反向查找
     */
    public static class Solution2 {
        public int jump(int[] nums) {
            int len = nums.length;
            int position = len - 1;
            int steps = 0;
            while (position > 0) {
                for (int i = 0; i < position - 1; i++) {
                    if (i + nums[i] >= position) {
                        steps++;
                        position = i;
                        break;
                    }
                }
            }
            return steps;
        }
    }

    public static void main(String[] args) {
        int[] nums = {3, 2, 2, 0, 4};
//        Solution solution = new Solution();
//        Solution1 solution = new Solution1();
        Solution2 solution = new Solution2();
        System.out.println(solution.jump(nums));
    }
}
