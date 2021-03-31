package com.hezk.algorithm.demo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Solution {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        TreeNode treeNode = sortedArrayToBST(nums);
        System.out.println(treeNode.val);
        System.out.println(preorderTraversal(treeNode));
        List<Integer> list = new ArrayList<>();
        preOrderTraversal(treeNode, list);
        System.out.println(list);
        System.out.println(inorderTraversal(treeNode));
        System.out.println(postorderTraversal(treeNode));
        depthOrderTraverse(treeNode);
        System.out.println();
        levelTraverse(treeNode);
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root.val > p.val && root.val > q.val) return lowestCommonAncestor(root.left, p, q);
        if (root.val < p.val && root.val < q.val) return lowestCommonAncestor(root.right, p, q);
        return root;
    }

    public static void levelTraverse(TreeNode root) {
        if (root == null) {
            return;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.print(node.val + "  ");
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }

    public static void depthOrderTraverse(TreeNode root) {
        if (root == null) {
            return;
        }
        LinkedList<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            System.out.print(node.val + "  ");
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
    }

    public static List<Integer> postorderTraversal(TreeNode root) {
        Stack<PowerNode> s = new Stack<PowerNode>();
        List<Integer> res = new LinkedList<Integer>();
        if (root != null) s.push(new PowerNode(root, false));
        while (!s.isEmpty()) {
            PowerNode curr = s.peek();
            //如果是第二次访问，就计算并pop该节点
            if (curr.visited) {
                res.add(curr.node.val);
                s.pop();
            } else {
                //如果是第一次访问，就将它的左右节点加入stack，并设置其已经访问了一次
                if (curr.node.right != null) s.push(new PowerNode(curr.node.right, false));
                if (curr.node.left != null) s.push(new PowerNode(curr.node.left, false));
                curr.visited = true;
            }
        }
        return res;
    }

    private static class PowerNode {
        TreeNode node;
        boolean visited;

        public PowerNode(TreeNode n, boolean v) {
            this.node = n;
            this.visited = v;
        }
    }

    private static void afterOrderTraversal(TreeNode root, List<Integer> list) {
        if (root != null) {
//            System.out.print(root.val + ",");
            afterOrderTraversal(root.left, list);
            afterOrderTraversal(root.right, list);
            list.add(root.val);
        }
    }

    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new LinkedList<Integer>();
        Stack<TreeNode> s = new Stack<TreeNode>();
        //先将最左边的节点都push进栈
        if (root != null) {
            pushAllTheLeft(s, root);
        }
        while (!s.isEmpty()) {
            TreeNode curr = s.pop();
            res.add(curr.val);
            //如果有右子树，将右节点和右子树的最左边的节点都push进栈
            if (curr.right != null) {
                pushAllTheLeft(s, curr.right);
            }
        }
        return res;
    }

    private static void pushAllTheLeft(Stack<TreeNode> s, TreeNode root) {
        s.push(root);
        while (root.left != null) {
            root = root.left;
            s.push(root);
        }
    }

    private static void preOrderTraversal(TreeNode root, List<Integer> list) {
        if (root != null) {
//            System.out.print(root.val + ",");
            list.add(root.val);
            preOrderTraversal(root.left, list);
            preOrderTraversal(root.right, list);
        }
    }

    public static List<Integer> preorderTraversal(TreeNode root) {
        Stack<TreeNode> s = new Stack<TreeNode>();
        List<Integer> res = new LinkedList<>();
        if (root != null) s.push(root);
        while (!s.isEmpty()) {
            TreeNode curr = s.pop();
            res.add(curr.val);
            if (curr.right != null) s.push(curr.right);
            if (curr.left != null) s.push(curr.left);
        }
        return res;
    }

    public static TreeNode sortedArrayToBST(int[] nums) {
        return buildTree(nums, 0, nums.length - 1);
    }

    private static TreeNode buildTree(int[] nums, int start, int end) {
        if (start > end) {
            return null;
        }
        int mid = start + (end - start) / 2;
        // 先递归的计算左子树
        TreeNode left = buildTree(nums, start, mid - 1);
        // 创造根节点
        TreeNode root = new TreeNode(nums[mid]);
        // 最后递归的计算右子树
        TreeNode right = buildTree(nums, mid + 1, end);
        root.left = left;
        root.right = right;
        return root;
    }
}