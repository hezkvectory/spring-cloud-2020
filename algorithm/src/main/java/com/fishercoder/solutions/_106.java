package com.fishercoder.solutions;

import com.fishercoder.common.classes.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * 106. Construct Binary Tree from Inorder and Postorder Traversal
 * <p>
 * Given inorder and postorder traversal of a tree, construct the binary tree.
 * <p>
 * Note:
 * You may assume that duplicates do not exist in the tree.
 */
public class _106 {
    public static class Solution1 {
        public TreeNode buildTree(int[] inorder, int[] postorder) {
            if (inorder == null || postorder == null || inorder.length != postorder.length) {
                return null;
            }
            HashMap<Integer, Integer> inorderMap = new HashMap<>();
            for (int i = 0; i < inorder.length; i++) {
                inorderMap.put(inorder[i], i);
            }
            return buildTreeRecursively(inorderMap, 0, inorder.length - 1, postorder, 0,
                    postorder.length - 1);
        }

        private TreeNode buildTreeRecursively(Map<Integer, Integer> inorderMap, int inorderStart,
                                              int inorderEnd, int[] postorder, int postorderStart, int postorderEnd) {
            if (postorderStart > postorderEnd || inorderStart > inorderEnd) {
                return null;
            }
            TreeNode root = new TreeNode(postorder[postorderEnd]);
            int inRoot = inorderMap.get(postorder[postorderEnd]);
            int numsLeft = inRoot - inorderStart;

            root.left = buildTreeRecursively(inorderMap, inorderStart, inRoot - 1, postorder, postorderStart, postorderStart + numsLeft - 1);
            root.right = buildTreeRecursively(inorderMap, inRoot + 1, inorderEnd, postorder, postorderStart + numsLeft, postorderEnd - 1);
            return root;
        }
    }
}
