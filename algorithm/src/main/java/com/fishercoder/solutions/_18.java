package com.fishercoder.solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 18. 4Sum
 * <p>
 * Given an array S of n integers, are there elements a, b, c, and d in S such that a + b + c + d = target?
 * Find all unique quadruplets in the array which gives the sum of target.
 * <p>
 * Note: The solution set must not contain duplicate quadruplets.
 * <p>
 * For example, given array S = [1, 0, -1, 0, -2, 2], and target = 0.
 * <p>
 * A solution set is:
 * [
 * [-1,  0, 0, 1],
 * [-2, -1, 1, 2],
 * [-2,  0, 0, 2]
 * ]
 */

public class _18 {

    public static class Solution1 {
        public List<List<Integer>> fourSum(int[] nums, int target) {
            List<List<Integer>> result = new ArrayList();
            if (nums == null || nums.length == 0) {
                return result;
            }
            Arrays.sort(nums);
            for (int i = 0; i < nums.length - 3; i++) {
                if (i > 0 && nums[i - 1] == nums[i]) {
                    continue;
                }
                for (int j = i + 1; j < nums.length - 2; j++) {
                    if (j > i + 1 && nums[j - 1] == nums[j]) {
                        continue;
                    }
                    int left = j + 1;
                    int right = nums.length - 1;
                    while (left < right) {
                        int sum = nums[i] + nums[j] + nums[left] + nums[right];
                        if (sum == target) {
                            result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                            while (left + 1 < right && nums[left] == nums[left + 1]) {
                                left++;
                            }
                            while (right - 1 > left && nums[right] == nums[right - 1]) {
                                right--;
                            }
                            left++;
                            right--;
                        } else if (sum > target) {
                            right--;
                        } else {
                            left++;
                        }
                    }
                }
            }
            return result;
        }
    }

    public static class Solution {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        int cur = 0;

        public List<List<Integer>> fourSum(int[] nums, int target) {
            Arrays.sort(nums);
            dfs(nums, target, 0);
            return ans;
        }

        void dfs(int[] nums, int target, int begin) {
            if (list.size() == 4) {
                if (cur == target) {
                    ans.add(new ArrayList<>(list));
                }
                return;
            }

            for (int i = begin; i < nums.length; i++) {
                if (nums.length - i < 4 - list.size()) {
                    return;
                }
                if (begin != i && nums[i - 1] == nums[i]) {
                    continue;
                }
                if (i < nums.length - 1 && cur + nums[i] + (3 - list.size()) * nums[i + 1] > target) {
                    return;
                }
                if (i < nums.length - 1 && cur + nums[i] + (3 - list.size()) * nums[nums.length - 1] < target) {
                    continue;
                }
                cur += nums[i];
                list.add(nums[i]);
                dfs(nums, target, i + 1);
                list.remove(list.size() - 1);
                cur -= nums[i];
            }
        }
    }

}
