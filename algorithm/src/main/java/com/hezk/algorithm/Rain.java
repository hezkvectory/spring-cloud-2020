package com.hezk.algorithm;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * #42 接雨水
 */
public class Rain {
    /**
     * 栈
     */
    public static class Solution {
        public int cal(int[] nums) {
            int len = nums.length;
            int res = 0;
            Deque<Integer> deque = new ArrayDeque<>();
            for (int i = 0; i < len; i++) {
                while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                    int temp = deque.removeLast();
                    if (deque.isEmpty()) {
                        break;
                    }
                    int distance = i - deque.peekLast() - 1;
                    int height = Math.min(nums[deque.peekLast()], nums[i]) - nums[temp];
                    res += distance * height;
                }
                deque.addLast(i);
            }
            return res;
        }
    }

    /**
     * 先计算，减少时间复杂度。相对于暴力解法
     */
    public static class Solution1 {
        public int cal(int[] nums) {
            int len = nums.length;
            int res = 0;

            int[] leftMax = new int[len];
            int[] rightMax = new int[len];

            leftMax[0] = nums[0];
            rightMax[len - 1] = nums[len - 1];

            for (int i = 1; i < len; i++) {
                leftMax[i] = Math.max(leftMax[i - 1], nums[i]);
            }

            for (int i = len - 2; i >= 0; i--) {
                rightMax[i] = Math.max(rightMax[i + 1], nums[i]);
            }

            for (int i = 0; i < len; i++) {
                res += Math.min(leftMax[i], rightMax[i]) - nums[i];
            }
            return res;
        }
    }

    /**
     * 双指针
     */
    public static class Solution2 {
        public int cal(int[] nums) {
            int len = nums.length;
            int res = 0;
            int leftMax = nums[0];
            int rightMax = nums[len - 1];

            int i = 1;
            while (i < len) {
                if (leftMax < rightMax) {
                    if (leftMax < nums[i]) {
                        leftMax = nums[i];
                    } else {
                        res += Math.min(leftMax, rightMax) - nums[i];
                    }
                    i++;
                } else {
                    if (rightMax < nums[len - 1]) {
                        rightMax = nums[len - 1];
                    } else {
                        res += Math.min(leftMax, rightMax) - nums[len - 1];
                    }
                    len--;
                }
            }
            return res;
        }
    }

    /**
     * 双指针
     */
    public static class Solution3 {
        public int cal(int[] height) {
            int left = 0, right = height.length - 1;
            int ans = 0;
            int left_max = 0, right_max = 0;
            while (left < right) {
                if (height[left] < height[right]) {
                    if (height[left] >= left_max) {
                        left_max = height[left];
                    } else {
                        ans += (left_max - height[left]);
                    }
                    ++left;
                } else {
                    if (height[right] >= right_max) {
                        right_max = height[right];
                    } else {
                        ans += (right_max - height[right]);
                    }
                    --right;
                }
            }
            return ans;
        }
    }

    public static void main(String[] args) {
        int[] nums = {4, 2, 0, 3, 2, 5};
        System.out.println(new Solution2().cal(nums));
        nums = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println(new Solution2().cal(nums));
    }
}
