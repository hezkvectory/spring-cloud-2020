package com.fishercoder.solutions;

import com.fishercoder.common.classes.TreeNode;
import com.fishercoder.common.utils.TreeUtils;

import java.util.*;

/**
 * 145. Binary Tree Postorder Traversal
 * <p>
 * Given a binary tree, return the postorder traversal of its nodes' values.
 * <p>
 * For example:
 * Given binary tree {1,#,2,3},
 * 1
 * \
 * 2
 * /
 * 3
 * return [3,2,1].
 * <p>
 * Note: Recursive solution is trivial, could you do it iteratively?
 */

public class _145 {

    public static void main(String[] args) {
        TreeNode root = TreeUtils.constructBinaryTree(Arrays.asList(1, null, 2, 3));
        TreeUtils.printBinaryTree(root);
        Solution1 solution = new Solution1();
        System.out.println(solution.postorderTraversal(root));
    }

    public static class Solution1 {
        /**
         * A tricky one: Modify the code for pre-order traversal
         * so that it becomes root->right->left,
         * and then reverse the result to get left->right->root.
         */
        public List<Integer> postorderTraversal(TreeNode root) {
            List<Integer> result = new ArrayList();
            if (root == null) {
                return result;
            }
            Stack<TreeNode> stack = new Stack();
            stack.push(root);
            while (!stack.isEmpty()) {
                root = stack.pop();
                result.add(root.val);
                if (root.left != null) {
                    stack.push(root.left);
                }
                if (root.right != null) {
                    stack.push(root.right);
                }
            }
            Collections.reverse(result);
            return result;
        }
    }

    public static class Solution2 {
        /**
         * Or use a LinkedList and add values to the head, then no reverse is needed.
         * the linked list contents get added like this:
         * <p>
         * root
         * right, root
         * left, right, root
         */
        public List<Integer> postorderTraversal(TreeNode root) {
            List<Integer> list = new LinkedList<>();
            if (root == null) {
                return list;
            }
            Stack<TreeNode> stack = new Stack<>();
            stack.push(root);
            while (!stack.isEmpty()) {
                TreeNode curr = stack.pop();
                list.add(0, curr.val);
                if (curr.left != null) {
                    stack.push(curr.left);
                }
                if (curr.right != null) {
                    stack.push(curr.right);
                }
            }
            return list;
        }
    }

    public static class Solution3 {
        public List<Integer> postorderTraversal(TreeNode root) {
            List<Integer> result = new ArrayList();
            return post(root, result);
        }

        List<Integer> post(TreeNode root, List<Integer> result) {
            if (root == null) {
                return result;
            }
            post(root.left, result);
            post(root.right, result);
            result.add(root.val);
            return result;
        }
    }
}
