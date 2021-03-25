package com.fishercoder.solutions;

import com.fishercoder.common.classes.TreeNode;

/**
 * 543. Diameter of Binary Tree
 * <p>
 * Given a binary tree, you need to compute the length of the diameter of the tree.
 * The diameter of a binary tree is the length of the longest path between any two nodes in a tree.
 * This path may or may not pass through the root.
 * <p>
 * Example:
 * Given a binary tree
 * 1
 * / \
 * 2   3
 * / \
 * 4   5
 * Return 3, which is the length of the path [4,2,1,3] or [5,2,1,3].
 * <p>
 * Note: The length of path between two nodes is represented by the number of edges between them.
 */
public class _543 {

    public static class Solution1 {
        /**
         * This is a very great problem for practicing recursion:
         * 1. What dfs() returns is the max height it should pick from either its left or right subtree, that's
         * what the int return type stands for;
         * 2. And during the recursion, we can keep updating the global variable: "diameter";
         * 3. When computing length/height of a subtree, we should take the max of its left and right, then plus one
         * and left height should be like this
         * int left = dfs(root.left);
         * instead of dfs(root.left) + 1;
         * we'll only plus one at the end
         */
        int diameter = 0;

        public int diameterOfBinaryTree(TreeNode root) {
            dfs(root);
            return diameter;
        }

        private int dfs(TreeNode root) {
            if (root == null) {
                return 0;
            }
            int left = dfs(root.left);
            int right = dfs(root.right);
            diameter = Math.max(diameter, left + right);
            return Math.max(left, right) + 1;
        }
    }

    public static class Solution2 {

        public int diameterOfBinaryTree(TreeNode root) {
            if (root == null) {
                return 0;
            }
            return help(root);
        }

        private int help(TreeNode root) {
            if (root == null) {
                return 0;
            }
            int rootSum = maxDepth(root.left) + maxDepth(root.right);
            int childSum = Math.max(help(root.left), help(root.right));
            return Math.max(rootSum, childSum);
        }

        public int maxDepth(TreeNode root) {
            if (root == null) {
                return 0;
            }
            return Math.max(maxDepth(root.left) + 1, maxDepth(root.right) + 1);
        }
    }

    public static class Solution3 {

        int max = 0;

        public int diameterOfBinaryTree(TreeNode root) {
            if (root == null) {
                return 0;
            }
            help(root);
            return max;
        }

        private int help(TreeNode root) {
            if (root == null) {
                return 0;
            }
            int left = root.left != null ? help(root.left) + 1 : 0;
            int right = root.right != null ? help(root.right) + 1 : 0;
            int cur = left + right;
            max = Math.max(max, cur);
            return Math.max(left, right);
        }

    }
}
