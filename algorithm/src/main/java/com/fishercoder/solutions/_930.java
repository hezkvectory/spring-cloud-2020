package com.fishercoder.solutions;

import java.util.HashMap;
import java.util.Map;

/**
 * 给你一个二元数组 nums ，和一个整数 goal ，请你统计并返回有多少个和为 goal 的 非空 子数组。
 * <p>
 * 子数组 是数组的一段连续部分。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [1,0,1,0,1], goal = 2
 * 输出：4
 * 解释：
 * 有 4 个满足题目要求的子数组：[1,0,1]、[1,0,1,0]、[0,1,0,1]、[1,0,1]
 * 示例 2：
 * <p>
 * 输入：nums = [0,0,0,0,0], goal = 0
 * 输出：15
 */
public class _930 {
    public static class Solution1 {
        public int numSubarraysWithSum(int[] nums, int goal) {
            Map<Integer, Integer> map = new HashMap<>();
            int preSum = 0;
            int count = 0;
            int len = nums.length;
            map.put(0, 1);
            for (int i = 0; i < len; i++) {
                preSum += nums[i];
                if (map.containsKey(preSum - goal)) {
                    count += map.getOrDefault(preSum - goal, 0);
                }
                map.put(preSum, map.getOrDefault(preSum, 0) + 1);
            }
            return count;
        }
    }
}
