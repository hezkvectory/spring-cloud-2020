package com.fishercoder.solutions;

import com.fishercoder.common.classes.TreeNode;
import com.fishercoder.common.utils.TreeUtils;

import java.util.*;

/**
 * 144. Binary Tree Preorder Traversal
 * <p>
 * Given a binary tree, return the preorder traversal of its nodes' values.
 * <p>
 * For example:
 * Given binary tree {1,#,2,3},
 * 1
 * \
 * 2
 * /
 * 3
 * return [1,2,3].
 * <p>
 * Note: Recursive solution is trivial, could you do it iteratively?
 */

public class _144 {
    public static void main(String[] args) {
        TreeNode root = TreeUtils.constructBinaryTree(Arrays.asList(1, null, 2, 3));
        TreeUtils.printBinaryTree(root);
        _145.Solution1 solution = new _145.Solution1();
        System.out.println(solution.postorderTraversal(root));
    }

    public static class Solution1 {
        public List<Integer> preorderTraversal(TreeNode root) {
            List<Integer> list = new ArrayList<>();
            Stack<TreeNode> stack = new Stack<>();
            stack.push(root);
            while (!stack.isEmpty()) {
                TreeNode curr = stack.pop();
                if (curr != null) {
                    list.add(curr.val);
                    stack.push(curr.right);
                    stack.push(curr.left);
                }
            }
            return list;
        }
    }

    public static class Solution2 {
        public List<Integer> preorderTraversal(TreeNode root) {
            List<Integer> list = new ArrayList();
            return pre(root, list);
        }

        List<Integer> pre(TreeNode root, List<Integer> list) {
            if (root == null) {
                return list;
            }
            list.add(root.val);
            pre(root.left, list);
            pre(root.right, list);
            return list;
        }
    }

    public static class Solution3 {
        public List<Integer> preorderTraversal(TreeNode root) {
            List<Integer> list = new ArrayList<>();
            Stack<TreeNode> stack = new Stack<>();
            while (!stack.isEmpty() || root != null) {
                while (root != null) {
                    list.add(root.val);
                    stack.push(root);
                    root = root.left;
                }
                root = stack.pop();
                root = root.right;
            }
            return list;
        }
    }
}
