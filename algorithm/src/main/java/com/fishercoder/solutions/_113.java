package com.fishercoder.solutions;

import com.fishercoder.common.classes.TreeNode;
import com.fishercoder.common.utils.TreeUtils;

import java.util.*;

/**
 * 113. Path Sum II
 * <p>
 * Given a binary tree and a sum, find all root-to-leaf paths where each path's sum equals the given sum.
 * <p>
 * For example:
 * Given the below binary tree and sum = 22,
 * 5
 * / \
 * 4   8
 * /   / \
 * 11  13  4
 * /  \    / \
 * 7    2  5   1
 * <p>
 * return
 * [
 * [5,4,11,2],
 * [5,8,4,5]
 * ]
 */
public class _113 {

    public static class Solution1 {
        public List<List<Integer>> pathSum(TreeNode root, int sum) {
            List<List<Integer>> allPaths = new ArrayList();
            if (root == null) {
                return allPaths;
            }
            dfs(root, new ArrayList(), allPaths, sum);
            return allPaths;
        }

        private void dfs(TreeNode root, List<Integer> path, List<List<Integer>> allPaths, int sum) {
            path.add(root.val);
            if (root.left != null) {
                dfs(root.left, path, allPaths, sum - root.val);
            }
            if (root.right != null) {
                dfs(root.right, path, allPaths, sum - root.val);
            }
            if (root.left == null && root.right == null) {
                /**Check if sum equals root.val, not sum equals zero!*/
                if (sum == root.val) {
                    allPaths.add(new ArrayList(path));
                }
            }
            path.remove(path.size() - 1);
        }
    }


    public static class Solution2 {

        private static final Deque<Integer> queue = new LinkedList<>();
        private static final List<List<Integer>> res = new ArrayList<>();

        public List<List<Integer>> pathSum(TreeNode root, int sum) {
            if (root == null) {
                return res;
            }
            dfs(root, sum);
            return res;
        }

        private void dfs(TreeNode root, int sum) {
            if (root == null) {
                return;
            }
            queue.addLast(root.val);
            dfs(root.left, sum - root.val);

            if (root.left == null && root.right == null) {
                if (root.val == sum) {
                    res.add(new LinkedList<>(queue));
                }
            }

            dfs(root.right, sum - root.val);
            queue.removeLast();
        }
    }

    public static class Solution3 {
        private static final Map<TreeNode, TreeNode> parent = new HashMap<>();

        public List<List<Integer>> pathSum(TreeNode root, int sum) {
            List<List<Integer>> res = new ArrayList<>();
            if (root == null) {
                return res;
            }
            Queue<TreeNode> queue = new LinkedList<>();
            Queue<Integer> sumQueue = new LinkedList<>();
            queue.offer(root);
            sumQueue.offer(root.val);
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                int tempSum = sumQueue.poll();
                if (node.left == null && node.right == null && tempSum == sum) {
                    res.add(getPath(node));
                    continue;
                }
                if (node.left != null) {
                    queue.offer(node.left);
                    sumQueue.offer(node.left.val + tempSum);
                    parent.put(node.left, node);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                    sumQueue.offer(node.right.val + tempSum);
                    parent.put(node.right, node);
                }
            }
            return res;
        }

        private List<Integer> getPath(TreeNode node) {
            List<Integer> res = new ArrayList<>();
            while (node != null) {
                res.add(0, node.val);
                node = parent.get(node);
            }
            return res;
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(11);
        root.left.left.left = new TreeNode(7);
        root.left.left.right = new TreeNode(2);

        root.right.left = new TreeNode(13);
        root.right.right = new TreeNode(4);
        root.right.right.left = new TreeNode(5);
        root.right.right.right = new TreeNode(1);

        Solution3 solution2 = new Solution3();
        TreeUtils.printBinaryTree(root);
        System.out.println(solution2.pathSum(root, 22));

    }
}
