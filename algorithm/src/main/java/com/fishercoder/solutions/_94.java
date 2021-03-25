package com.fishercoder.solutions;

import com.fishercoder.common.classes.TreeNode;
import com.fishercoder.common.utils.TreeUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 94. Binary Tree Inorder Traversal
 * <p>
 * Given a binary tree, return the inorder traversal of its nodes' values.
 * <p>
 * For example:
 * Given binary tree [1,null,2,3],
 * 1
 * \
 * 2
 * /
 * 3
 * return [1,3,2].
 * <p>
 * Note: Recursive solution is trivial, could you do it iteratively?
 */

public class _94 {

    public static void main(String[] args) {
        TreeNode root = TreeUtils.constructBinaryTree(Arrays.asList(1, null, 2, 3));
        TreeUtils.printBinaryTree(root);
        Solution4 solution = new Solution4();
        System.out.println(solution.inorderTraversal(root));
    }

    public static class Solution1 {
        public List<Integer> inorderTraversal(TreeNode root) {
            return inorder(root, new ArrayList());
        }

        private List<Integer> inorder(TreeNode root, List<Integer> result) {
            if (root == null) {
                return result;
            }
            inorder(root.left, result);
            result.add(root.val);
            return inorder(root.right, result);
        }
    }

    public static class Solution4 {
        public List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            inorder(root, result);
            return result;
        }

        public List<Integer> inorder(TreeNode root, List<Integer> list) {
            if (root == null) {
                return list;
            }
            inorder(root.left, list);
            list.add(root.val);
            inorder(root.right, list);
            return list;
        }
    }

    public static class Solution2 {
        //iterative approach
        public List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> result = new ArrayList();
            Stack<TreeNode> stack = new Stack();
            while (root != null || !stack.isEmpty()) {
                while (root != null) {
                    stack.push(root);
                    root = root.left;
                }
                root = stack.pop();
                result.add(root.val);
                root = root.right;
            }
            return result;
        }
    }


    public static class Solution3 {
        public List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            Stack<TreeNode> stack = new Stack<>();
            while (!stack.isEmpty() || root != null) {
                while (root != null) {
                    stack.push(root);
                    root = root.left;
                }
                root = stack.pop();
                result.add(root.val);
                root = root.right;
            }
            return result;
        }
    }
}
