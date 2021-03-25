package com.fishercoder.solutions;

import com.fishercoder.common.classes.TreeNode;
import com.fishercoder.common.utils.TreeUtils;

import java.util.*;

/**199. Binary Tree Right Side View

Given a binary tree, imagine yourself standing on the right side of it, return the values of the nodes you can see ordered from top to bottom.

For example:
Given the following binary tree,

   1            <---
 /   \
2     3         <---
 \     \
  5     4       <---

You should return [1, 3, 4]. */

public class _199 {
	public static void main(String[] args) {
		TreeNode root = TreeUtils.constructBinaryTree(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
		TreeUtils.printBinaryTree(root);
		Solution1 solution = new Solution1();
		System.out.println(solution.rightSideView(root));
	}

	public static class Solution1 {
		/**credit: https://leetcode.com/problems/binary-tree-right-side-view/discuss/56012/My-simple-accepted-solution(JAVA)*/
		public List<Integer> rightSideView(TreeNode root) {
			List<Integer> result = new ArrayList<>();
			rightView(root, result, 0);
			return result;
		}

		void rightView(TreeNode curr, List<Integer> result, int currDepth) {
			if (curr == null) {
				return;
			}
//			System.out.print(curr.val + "\t");
			if (currDepth == result.size()) {
				result.add(curr.val);
			}
			rightView(curr.right, result, currDepth + 1);
			rightView(curr.left, result, currDepth + 1);
		}
	}

	public static class Solution2 {
		/**BFS the tree*/
		public List<Integer> rightSideView(TreeNode root) {
			List<Integer> result = new ArrayList<>();
			if (root == null) {
				return result;
			}
			Queue<TreeNode> q = new LinkedList<>();
			q.offer(root);
			while (!q.isEmpty()) {
				int size = q.size();
				for (int i = 0; i < size; i++) {
					TreeNode curr = q.poll();
					if (i == size - 1) {
						result.add(curr.val);
					}
					if (curr.left != null) {
						q.offer(curr.left);
					}
					if (curr.right != null) {
						q.offer(curr.right);
					}
				}
			}
			return result;
		}
	}

}