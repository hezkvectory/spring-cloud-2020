package com.fishercoder.solutions;

import com.fishercoder.common.classes.TreeNode;
import com.fishercoder.common.utils.TreeUtils;

import java.util.*;

/**
 * 236. Lowest Common Ancestor of a Binary Tree
 *
 * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
 * According to the definition of LCA on Wikipedia:
 * “The lowest common ancestor is defined between two nodes v and w as the lowest node in T
 * that has both v and w as descendants (where we allow a node to be a descendant of itself).”

           _______3______
          /              \
      ___5__          ___1__
    /      \        /      \
   6      _2       0       8
  / \
 7   4

 For example, the lowest common ancestor (LCA) of nodes 5 and 1 is 3.
 Another example is LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the LCA definition.*/

public class _236 {
    public static void main(String[] args) {
        TreeNode root = TreeUtils.constructBinaryTree(Arrays.asList(3,5,1,6,2,0,8,7,4));
        TreeUtils.printBinaryTree(root);
        Solution1 solution = new Solution1();
        TreeNode common = solution.lowestCommonAncestor(root, root.left.left.left,root.left.left.right);
        System.out.println(common.val);
    }
    public static class Solution1 {

        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            if (root == null || root == p || root == q) {
                return root;
            }
            TreeNode left = lowestCommonAncestor(root.left, p, q);
            TreeNode right = lowestCommonAncestor(root.right, p, q);
            if (left != null && right != null) {
                return root;
            }
            return left != null ? left : right;
        }
    }


    public static class Solution3 {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            Map<TreeNode, TreeNode> map = new HashMap<>();
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            while(!queue.isEmpty()) {
                int size = queue.size();
                for(int i = 0;i<size;i++){
                    TreeNode tmp = queue.poll();
                    if (tmp.left != null) {
                        queue.offer(tmp.left);
                        map.put(tmp.left, tmp);
                    }
                    if(tmp.right != null) {
                        queue.offer(tmp.right);
                        map.put(tmp.right, tmp);
                    }
                }
            }
            Set<TreeNode> set = new HashSet<>();
            while(p != null) {
                set.add(p);
                p = map.get(p);
            }
            while(q != null) {
                if (set.contains(q)) {
                    return q;
                }
                q = map.get(q);
            }
            return null;
        }
    }

}
