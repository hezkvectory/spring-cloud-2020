package com.hezk.algorithm;

import com.fishercoder.common.classes.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

public class SymmetryTree {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(3);

        Solution1 solution = new Solution1();
        boolean same = solution.isSymmetric(root);
        System.out.println(same);
    }

    public static class Solution {
        public boolean isSymmetric(TreeNode root) {
            return check(root, root);
        }

        private boolean check(TreeNode p, TreeNode q) {
            if (p == null && q == null) {
                return true;
            }
            if (p == null || q == null || p.val != q.val) {
                return false;
            }
            return check(p.left, q.right) && check(p.right, q.left);
        }
    }

    public static class Solution1 {
        public boolean isSymmetric(TreeNode root) {
            if (root == null) {
                return true;
            }
            Queue<TreeNode> q1 = new LinkedList<>();
            Queue<TreeNode> q2 = new LinkedList<>();
            q1.offer(root);
            q2.offer(root);

            while (!q1.isEmpty() && !q2.isEmpty()) {
                TreeNode n1 = q1.poll();
                TreeNode n2 = q2.poll();
                if (n1 == null && n2 == null) {
                    continue;
                }
                if (n1 == null || n2 == null || n1.val != n2.val) {
                    return false;
                }
                q1.offer(n1.left);
                q2.offer(n2.right);

                q1.offer(n1.right);
                q2.offer(n2.left);
            }
            return q1.isEmpty() && q2.isEmpty();
        }
    }
}
