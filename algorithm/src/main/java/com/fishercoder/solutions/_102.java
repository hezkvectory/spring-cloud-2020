package com.fishercoder.solutions;

import com.fishercoder.common.classes.TreeNode;
import com.fishercoder.common.utils.TreeUtils;

import java.util.*;

/**
 * 102. Binary Tree Level Order Traversal
 * <p>
 * Given a binary tree, return the level order traversal of its nodes' values. (ie, from left to right, level by level).
 * <p>
 * For example:
 * Given binary tree [3,9,20,null,null,15,7],
 * 3
 * / \
 * 9  20
 * /  \
 * 15   7
 * <p>
 * return its level order traversal as:
 * <p>
 * [
 * [3],
 * [9,20],
 * [15,7]
 * ]
 */
public class _102 {

    public static void main(String[] args) {
        TreeNode root = TreeUtils.constructBinaryTree(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        TreeUtils.printBinaryTree(root);
        Solution1 solution = new Solution1();
        System.out.println(solution.levelOrder(root));
    }

    public static class Solution1 {
        public List<List<Integer>> levelOrder(TreeNode root) {
            List<List<Integer>> result = new ArrayList<>();
            if (root == null) {
                return result;
            }
            Queue<TreeNode> queue = new LinkedList();
            queue.offer(root);
            while (!queue.isEmpty()) {
                List<Integer> thisLevel = new ArrayList();
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    TreeNode curr = queue.poll();
                    thisLevel.add(curr.val);
                    if (curr.left != null) {
                        queue.offer(curr.left);
                    }
                    if (curr.right != null) {
                        queue.offer(curr.right);
                    }
                }
                result.add(thisLevel);
            }
            return result;
        }
    }
}
