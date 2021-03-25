package com.fishercoder.solutions;

import com.fishercoder.common.classes.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 563. Binary Tree Tilt
 * <p>
 * Given a binary tree, return the tilt of the whole tree.
 * The tilt of a tree node is defined as the absolute difference between the sum of all left subtree node values
 * and the sum of all right subtree node values.
 * Null node has tilt 0.
 * The tilt of the whole tree is defined as the sum of all nodes' tilt.
 * <p>
 * Example:
 * Input:
 * 1
 * /   \
 * 2     3
 * <p>
 * Output: 1
 * <p>
 * Explanation:
 * Tilt of node 2 : 0
 * Tilt of node 3 : 0
 * Tilt of node 1 : |2-3| = 1
 * Tilt of binary tree : 0 + 0 + 1 = 1
 * <p>
 * Note:
 * The sum of node values in any subtree won't exceed the range of 32-bit integer.
 * All the tilt values won't exceed the range of 32-bit integer.
 */
public class _563 {
    public static class Solution1 {

        int tilt = 0;

        public int findTilt(TreeNode root) {
            findTiltDfs(root);
            return tilt;
        }

        public int findTiltDfs(TreeNode root) {
            if (root == null) {
                return 0;
            }
            int leftTilt = 0;
            if (root.left != null) {
                leftTilt = findTiltDfs(root.left);
            }
            int rightTilt = 0;
            if (root.right != null) {
                rightTilt = findTiltDfs(root.right);
            }
            if (root.left == null && root.right == null) {
                return root.val;
            }
            tilt += Math.abs(leftTilt - rightTilt);
            return leftTilt + rightTilt + root.val;
        }
    }

    public static class Solution2 {
        int cal = 0;

        public int cal(TreeNode root) {
            if (root == null) {
                return 0;
            }
            int left = cal(root.left);
            int right = cal(root.right);
            cal += Math.abs(left - right);
            return left + right + root.val;
        }
    }

    public static class Solution3 {

        public int cal(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            depthDiff(root, result);
            return result.stream().mapToInt(n -> n).sum();
        }

        public int depthDiff(TreeNode root, List<Integer> result) {
            if (root == null) {
                return 0;
            }
            int left = depthDiff(root.left, result);
            int right = depthDiff(root.right, result);
            result.add(Math.abs(left - right));
            return left + right + root.val;
        }

    }

}
