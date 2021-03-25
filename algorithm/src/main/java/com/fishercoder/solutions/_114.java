package com.fishercoder.solutions;

import com.fishercoder.common.classes.TreeNode;
import com.fishercoder.common.utils.TreeUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 114. Flatten Binary Tree to Linked List
 * <p>
 * Given a binary tree, flatten it to a linked list in-place.
 * <p>
 * For example,
 * Given
 * <p>
 * 1
 * / \
 * 2  5
 * / \  \
 * 3 4  6
 * <p>
 * The flattened tree should look like:
 * 1
 * \
 * 2
 * \
 * 3
 * \
 * 4
 * \
 * 5
 * \
 * 6
 * <p>
 * Hints:
 * If you notice carefully in the flattened tree, each node's right child points to the next node of a pre-order traversal.
 */
public class _114 {

    public static class Solution1 {
        public void flatten(TreeNode root) {
            while (root != null) {
                if (root.left != null) {
                    TreeNode previousNode = root.left;
                    while (previousNode.right != null) {
                        previousNode = previousNode.right;
                    }
                    previousNode.right = root.right;
                    root.right = root.left;
                    root.left = null;
                }
//                TreeUtils.printBinaryTree(root);
                root = root.right;
            }
        }
    }


    public static class Solution2 {
        public void flatten(TreeNode root) {
            if (root == null) {
                return;
            }
            Stack<TreeNode> stack = new Stack<>();
            Map<TreeNode, Boolean> visited = new HashMap<>();
            TreeNode p = root;
            while (!stack.isEmpty() || p != null) {
                while (p != null) {
                    stack.push(p);
                    p = p.left;
                }
                TreeNode node = stack.peek();
                if (node.right != null && !visited.getOrDefault(node, false)) {
                    p = node.right;
                    visited.put(node, true);
                } else {
                    if (node.left != null) {
                        TreeNode node1 = node.left;
                        while (node1.right != null) {
                            node1 = node1.right;
                        }
                        node1.right = node.right;
                        node.right = node.left;
                        node.left = null;
                    }
                    stack.pop();
                }
                TreeUtils.printBinaryTree(root);
            }
        }
    }


    public static class Solution {
        public void flatten(TreeNode root) {
            TreeNode curr = root;
            while (curr != null) {
                if (curr.left != null) {
                    TreeNode prev = curr.left;
                    while (prev.right != null) {
                        prev = prev.right;
                    }
                    prev.right = curr.right;
                    curr.right = curr.left;
                    curr.left = null;
                }
                curr = curr.right;
            }
        }
    }


    public static void main(String[] args) {
        TreeNode root = TreeUtils.constructBinaryTree(Arrays.asList(1, 2, 5, 3, 4, null, 6));
        TreeUtils.printBinaryTree(root);

        Solution solution = new Solution();
        solution.flatten(root);
        TreeUtils.printBinaryTree(root);
    }


}
