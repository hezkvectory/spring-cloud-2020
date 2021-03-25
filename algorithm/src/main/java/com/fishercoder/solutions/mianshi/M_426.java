package com.fishercoder.solutions.mianshi;

import com.fishercoder.common.classes.TreeNode;

public class M_426 {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);

        Solution solution = new Solution();
        TreeNode head = solution.treeToDoublyList(root);
        System.out.println(head);
    }

    public static class Solution {
        TreeNode pre, head;

        public TreeNode treeToDoublyList(TreeNode root) {
            if (root == null) {
                return null;
            }
            dfs(root);
            head.left = pre;
            pre.right = head;
            return head;
        }

        void dfs(TreeNode root) {
            if (root == null) {
                return;
            }
            dfs(root.left);
            if (pre == null) {
                head = root;
                pre = root;
            } else {
                pre.right = root;
                root.left = pre;
                pre = root;
            }
            dfs(root.right);
        }
    }

}
