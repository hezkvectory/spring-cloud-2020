package com.fishercoder.solutions;

import com.fishercoder.common.classes.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 101. Symmetric Tree

Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).

For example, this binary tree [1,2,2,3,4,4,3] is symmetric:

    1
   / \
  2   2
 / \ / \
3  4 4  3

But the following [1,2,2,null,3,null,3] is not:

    1
   / \
  2   2
   \   \
   3    3

Note:
Bonus points if you could solve it both recursively and iteratively.
 */

public class _101 {
	public static class Solution1 {
		public boolean isSymmetric(TreeNode root) {
			if (root == null) {
				return true;
			}
			return isSymmetric(root.left, root.right);
		}

		private boolean isSymmetric(TreeNode left, TreeNode right) {
			if (left == null || right == null) {
				return left == right;
			}
			return left.val == right.val && isSymmetric(left.left, right.right) && isSymmetric(left.right, right.left);
		}
	}

	public static class Solution2 {
		public boolean isSymmetric(TreeNode root) {
			if (root == null) {
				return true;
			}
			return isSymmetric(root.left, root.right);
		}

		private boolean isSymmetric(TreeNode left, TreeNode right) {
			if (left == null && right == null) {
				return true;
			}
			if (left != null || right != null || right.val != left.val) {
				return false;
			}
			return isSymmetric(left.left, right.right) && isSymmetric(left.right, right.left);
		}
	}

	public static class Solution3 {
		public boolean isSymmetric(TreeNode root) {
			if (root == null) {
				return true;
			}
			Queue<TreeNode> queue = new LinkedList<>();
			queue.offer(root.left);
			queue.offer(root.right);
			while(!queue.isEmpty()) {
				TreeNode node1 = queue.poll();
				TreeNode node2 = queue.poll();
				if (node1== null && node2 == null) {
					return true;
				}
				if (node1 != null || node2 != null || node1.val != node2.val) {
					return false;
				}
				queue.offer(node1.left);
				queue.offer(node2.right);
				queue.offer(node1.right);
				queue.offer(node2.left);
			}
			return true;
		}
	}
}
