package com.fishercoder.solutions;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 39. Combination Sum
 * <p>
 * Given a set of candidate numbers (C) (without duplicates) and a target number (T),
 * find all unique combinations in C where the candidate numbers sums to T.
 * <p>
 * The same repeated number may be chosen from C unlimited number of times.
 * <p>
 * Note:
 * All numbers (including target) will be positive integers.
 * The solution set must not contain duplicate combinations.
 * For example, given candidate set [2, 3, 6, 7] and target 7,
 * A solution set is:
 * [
 * [7],
 * [2, 2, 3]
 * ]
 */
public class _39 {

    public static class Solution1 {
        public List<List<Integer>> combinationSum(int[] candidates, int target) {
            List<List<Integer>> result = new ArrayList();
            Arrays.sort(candidates);
            backtracking(candidates, target, 0, new ArrayList(), result);
            return result;
        }

        void backtracking(int[] candidates, int target, int start, List<Integer> curr, List<List<Integer>> result) {
            if (target > 0) {
                for (int i = start; i < candidates.length; i++) {
                    if (candidates[i] > target) {
                        continue;//pruning
                    }
                    curr.add(candidates[i]);
                    backtracking(candidates, target - candidates[i], i, curr, result);
                    curr.remove(curr.size() - 1);
                }
            } else if (target == 0) {
                result.add(new ArrayList(curr));
            }
        }
    }


    public static class Solution {
        public static List<List<Integer>> combinationSum(int[] candidates, int target) {
            List<List<Integer>> ans = new ArrayList<>();
            if (candidates == null) {
                return ans;
            }
            dfs(ans, new ArrayDeque<>(), candidates, target, 0);
            return ans;
        }

        private static void dfs(List<List<Integer>> ans, ArrayDeque<Integer> curr, int[] candidates, int target, int start) {
            if (target < 0) {
                return;
            }
            if (target == 0) {
                ans.add(new ArrayList<>(curr));
                return;
            }
            for (int i = start; i < candidates.length; i++) {
                curr.addLast(candidates[i]);
                dfs(ans, curr, candidates, target - candidates[i], i);
                curr.removeLast();
            }
        }
    }


}
