package com.fishercoder.solutions;

import java.util.Arrays;

/**
 * 169. Majority Element
 * <p>
 * Given an array of size n, find the majority element. The majority element is the element that appears more than ⌊ n/2 ⌋ times.
 * You may assume that the array is non-empty and the majority element always exist in the array.
 * <p>
 * Example 1:
 * Input: [3,2,3]
 * Output: 3
 * <p>
 * Example 2:
 * Input: [2,2,1,1,1,2,2]
 * Output: 2
 */
public class _169 {

    public static void main(String[] args) {
        Solution4 solution = new Solution4();
        System.out.println(solution.majorityElement(new int[]{2, 2, 1, 1, 1, 2, 2}));
    }

    public static class Solution {
        public int majorityElement(int[] nums) {
            int count = 1, majority = nums[0];
            for (int i = 1; i < nums.length; i++) {
                if (majority == nums[i]) {
                    count++;
                } else if (--count == 0) {
                    count = 1;
                    majority = nums[i];
                }
            }
            return majority;
        }
    }

    public static class Solution1 {
        public int majorityElement(int[] nums) {
            Arrays.sort(nums);
            return nums[nums.length >> 1];
        }
    }

    public static class Solution4 {
        /**
         * 采用分治思想，递归思路。
         * 1、确定切分的终止条件，直到所有的子问题都是长度为 1 的数组，停止切分。
         * 2、拆分数组，递归地将原数组二分为左区间与右区间，直到最终的数组只剩下一个元素，将其返回
         * 3、处理子问题得到子结果，并合并
         * 3.1 长度为 1 的子数组中唯一的数显然是众数，直接返回即可。
         * 3.2 如果它们的众数相同，那么显然这一段区间的众数是它们相同的值。
         * 3.3 如果他们的众数不同，比较两个众数在整个区间内出现的次数来决定该区间的众数
         */
        public int majorityElement(int[] nums) {
            if (nums.length < 1) {
                return 0;
            }
            return help(nums, 0, nums.length - 1);
        }

        private int help(int[] nums, int start, int end) {
            // 1、拆分数组，直到剩下最后一个 一定为众数
            if (start == end) {
                return nums[start];
            }
            // 2、处理子问题
            int mid = start + (end - start) / 2;
            int left = help(nums, start, mid);
            int right = help(nums, mid + 1, end);
            //  如果它们的众数相同，那么显然这一段区间的众数是它们相同的值。
            if (left == right)
                return left;

            // 统计左右区间的众数
            int leftCount = countElement(nums, left, start, end);
            int rightCount = countElement(nums, right, start, end);

            return leftCount > rightCount ? left : right;
        }

        private int countElement(int[] nums, int num, int start, int end) {
            int count = 0;
            for (int i = start; i <= end; i++) {
                if (num == nums[i])
                    count++;
            }
            return count;
        }
    }

    public static class Solution2 {
        /**
         * Moore Voting Algorithm
         * How to understand this:
         * 1. For a number to qualify as a majority element, it needs to occur more than 1/2 times, which
         * means there are a max of only one such element in any given array.
         * 2. E.g. given this array [1,2,3,1,1,1], two of the 1s will be balanced off by 2 and 3, still there are two 1s remaining in the end
         * which is the majority element
         */
        public int majorityElement(int[] nums) {
            int count = 1;
            int majority = nums[0];
            for (int i = 1; i < nums.length; i++) {
                if (count == 0) {
                    count++;
                    majority = nums[i];
                } else if (nums[i] == majority) {
                    count++;
                } else {
                    count--;
                }
            }
            return majority;
        }
    }

    public static class Solution3 {
        //bit manipulation
        public int majorityElement(int[] nums) {
            int[] bit = new int[32];//because an integer is 32 bits, so we use an array of 32 long
            for (int num : nums) {
                for (int i = 0; i < 32; i++) {
                    if ((num >> (31 - i) & 1) == 1) {
                        bit[i]++;//this is to compute each number's ones frequency
                    }
                }
            }
            int res = 0;
            //this below for loop is to construct the majority element: since every bit of this element would have appeared more than n/2 times
            for (int i = 0; i < 32; i++) {
                bit[i] = bit[i] > nums.length / 2 ? 1
                        : 0;//we get rid of those that bits that are not part of the majority number
                res += bit[i] * (1 << (31 - i));
            }
            return res;
        }
    }
}
