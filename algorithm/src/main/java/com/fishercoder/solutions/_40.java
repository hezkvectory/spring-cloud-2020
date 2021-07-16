package com.fishercoder.solutions;

import java.util.*;

/**
 * 40. Combination Sum II
 * <p>
 * Given a collection of candidate numbers (C) and a target number (T), find all unique combinations in C where the candidate numbers sums to T.
 * Each number in C may only be used once in the combination.
 * <p>
 * Note:
 * All numbers (including target) will be positive integers.
 * The solution set must not contain duplicate combinations.
 * For example, given candidate set [10, 1, 2, 7, 6, 1, 5] and target 8,
 * A solution set is:
 * [
 * [1, 7],
 * [1, 2, 5],
 * [2, 6],
 * [1, 1, 6]
 * ]
 */
public class _40 {

    public static class Solution1 {
        public List<List<Integer>> combinationSum2(int[] candidates, int target) {
            List<List<Integer>> result = new ArrayList();
            Arrays.sort(candidates);
            backtracking(candidates, 0, result, target, new ArrayList());
            return result;
        }

        void backtracking(int[] candidates, int start, List<List<Integer>> result, int target, List<Integer> curr) {
            if (target > 0) {
                for (int i = start; i < candidates.length; i++) {
                    if (candidates[i] > target || (i > start && candidates[i - 1] == candidates[i])) {
                        continue;
                    }
                    curr.add(candidates[i]);
                    backtracking(candidates, i + 1, result, target - candidates[i], curr);
                    curr.remove(curr.size() - 1);
                }
            } else if (target == 0) {
                result.add(new ArrayList(curr));
            }
        }
    }


    public static class Solution {
        public List<List<Integer>> combinationSum2(int[] candidates, int target) {
            List<List<Integer>> ans = new ArrayList<>();
            if (candidates.length == 0) {
                return ans;
            }
            Arrays.sort(candidates);
            Deque<Integer> deque = new ArrayDeque<>();
            dfs(ans, deque, candidates, target, 0);
            return ans;
        }

        private void dfs(List<List<Integer>> ans, Deque<Integer> deque, int[] candidates, int target, int start) {
            if (target < 0) {
                return;
            }
            if (target == 0) {
                ans.add(new ArrayList<>(deque));
                return;
            }
            for (int i = start; i < candidates.length; i++) {
                if (candidates[i] > target || (i > start && candidates[i - 1] == candidates[i])) {
                    continue;
                }
                deque.add(candidates[i]);
                dfs(ans, deque, candidates, target - candidates[i], i + 1);
                deque.removeLast();
            }
        }
    }


}
