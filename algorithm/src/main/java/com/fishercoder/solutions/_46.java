package com.fishercoder.solutions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 46. Permutations
 * <p>
 * Given a collection of distinct numbers, return all possible permutations.
 * <p>
 * For example,
 * [1,2,3] have the following permutations:
 * [
 * [1,2,3],
 * [1,3,2],
 * [2,1,3],
 * [2,3,1],
 * [3,1,2],
 * [3,2,1]
 * ]
 */

public class _46 {

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.permute(new int[]{1, 2, 3}));
    }

    static class Solution {
        private void backtrack(int n, ArrayList<Integer> nums, List<List<Integer>> output, int first) {
            // if all integers are used up
            if (first == n) {
                output.add(new ArrayList<>(nums));
            }
            for (int i = first; i < n; i++) {
                // place i-th integer first
                // in the current permutation
                Collections.swap(nums, first, i);
                // use next integers to complete the permutations
                backtrack(n, nums, output, first + 1);
                // backtrack
                Collections.swap(nums, first, i);
            }
        }

        public List<List<Integer>> permute(int[] nums) {
            List<List<Integer>> output = new LinkedList();

            ArrayList<Integer> nums_lst = new ArrayList<>();
            for (int num : nums)
                nums_lst.add(num);

            backtrack(nums.length, nums_lst, output, 0);
            return output;
        }
    }


    public static class Solution1 {
        public List<List<Integer>> permute(int[] nums) {
            List<List<Integer>> result = new ArrayList();
            result.add(new ArrayList<>());
            return backtracking(nums, 0, result);
        }

        private List<List<Integer>> backtracking(int[] nums, int index, List<List<Integer>> result) {
            if (index == nums.length) {
                return result;
            }
            List<List<Integer>> newResult = new ArrayList<>();
            for (List<Integer> eachList : result) {
                for (int i = 0; i <= eachList.size(); i++) {
                    List<Integer> newList = new ArrayList<>(eachList);
                    newList.add(i, nums[index]);
                    newResult.add(newList);
                }
            }
            result = newResult;
            return backtracking(nums, index + 1, result);
        }
    }

    public static class Solution3 {

        public static List<List<Integer>> permute(List<Integer> list) {
            List<List<Integer>> result = new ArrayList();
            int len = list.size();
            dfs(result, list, new ArrayList(), len);
            return result;
        }

        public static void dfs(List<List<Integer>> result, List<Integer> list, List<Integer> temp, int len) {
            if (temp.size() == len) {
                result.add(new ArrayList(temp));
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                if (temp.contains(list.get(i))) {
                    continue;
                }
                temp.add(list.get(i));
                dfs(result, list, temp, len);
                temp.remove(temp.size() - 1);
            }
        }
    }


    public static class Solution4 {

        public static List<List<Integer>> permute(List<Integer> list) {
            List<List<Integer>> result = new ArrayList();
            dfs(result, list, 0);
            return result;
        }

        public static void dfs(List<List<Integer>> result, List<Integer> list, int start) {
            if (start == list.size()) {
                result.add(new ArrayList(list));
                return;
            }
            for (int i = start; i < list.size(); i++) {
                Collections.swap(list, i, start);
                dfs(result, list, start + 1);
                Collections.swap(list, i, start);
            }
        }
    }
}
