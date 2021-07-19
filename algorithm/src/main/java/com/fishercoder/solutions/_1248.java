package com.fishercoder.solutions;

import java.util.HashMap;
import java.util.Map;

/**
 * 给你一个整数数组 nums 和一个整数 k。
 * <p>
 * 如果某个 连续 子数组中恰好有 k 个奇数数字，我们就认为这个子数组是「优美子数组」。
 * <p>
 * 请返回这个数组中「优美子数组」的数目。
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [1,1,2,1,1], k = 3
 * 输出：2
 * 解释：包含 3 个奇数的子数组是 [1,1,2,1] 和 [1,2,1,1] 。
 * <p>
 * 示例 2：
 * <p>
 * 输入：nums = [2,4,6], k = 1
 * 输出：0
 * 解释：数列中不包含任何奇数，所以不存在优美子数组。
 * <p>
 * 示例 3：
 * <p>
 * 输入：nums = [2,2,2,1,2,2,1,2,2,2], k = 2
 * 输出：16
 */
public class _1248 {
    /**
     * 超时
     */
    public static class Solution1 {
        public int numberOfSubarrays(int[] nums, int k) {
            Map<Integer, Integer> map = new HashMap<>();
            int len = nums.length;
            int count = 0;
            map.put(0, 0);
            for (int i = 0; i < len; i++) {
                map.put(i + 1, map.get(i) + (nums[i] % 2 == 1 ? 1 : 0));
            }
            for (int i = 0; i <= len; i++) {
                for (int j = i; j <= len; j++) {
                    if (map.get(j) - map.get(i) == k) {
                        count++;
                    }
                }
            }
            return count;
        }
    }


    public static class Solution2 {
        public int numberOfSubarrays(int[] nums, int k) {
            int len = nums.length;
            if (len <= 0) {
                return 0;
            }
            int count = 0;
            Map<Integer, Integer> map = new HashMap<>();
            map.put(0, 1);
            int oddNum = 0;
            for (int x : nums) {
                oddNum += x & 1;
                if (map.containsKey(oddNum - k)) {
                    count += map.get(oddNum - k);
                }
                map.put(oddNum, map.getOrDefault(oddNum, 0) + 1);
            }
            return count;
        }
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        Solution2 solution1 = new Solution2();
        System.out.println(solution1.numberOfSubarrays(nums, 1));
    }
}
