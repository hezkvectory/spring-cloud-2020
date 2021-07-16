package com.fishercoder.solutions.mianshi;

public class M_33 {
    public static class Solution {
        public boolean verifyPostorder(int[] postorder) {
            return recur(postorder, 0, postorder.length - 1);
        }

        boolean recur(int[] postorder, int i, int j) {
            if (i >= j) return true;
            int p = i;
            while (postorder[p] < postorder[j]) p++;
            int m = p;
            while (postorder[p] > postorder[j]) p++;
            return p == j && recur(postorder, i, m - 1) && recur(postorder, m, j - 1);
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.verifyPostorder(new int[]{1, 3, 2, 6, 5}));
        System.out.println(solution.verifyPostorder(new int[]{1, 6, 3, 2, 5}));
    }
}
