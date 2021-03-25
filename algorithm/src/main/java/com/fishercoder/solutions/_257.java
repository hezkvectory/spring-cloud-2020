package com.fishercoder.solutions;

import com.fishercoder.common.classes.TreeNode;
import com.fishercoder.common.utils.TreeUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class _257 {
    public static class Solution1 {
        //a very typical/good question to test your recursion/dfs understanding.
        public List<String> binaryTreePaths_more_concise(TreeNode root) {
            List<String> paths = new ArrayList<>();
            if (root == null) {
                return paths;
            }
            dfs(root, paths, "");
            return paths;
        }

        private void dfs(TreeNode root, List<String> paths, String path) {
            if (root.left == null && root.right == null) {
                paths.add(path + root.val);
                return;
            }
            path += root.val + "->";
            if (root.left != null) {
                dfs(root.left, paths, path);
            }
            if (root.right != null) {
                dfs(root.right, paths, path);
            }
        }
    }

    public static class Solution2 {
        public List<String> binaryTreePaths(TreeNode root) {
            List<String> paths = new ArrayList<>();
            dfs(root, paths, new StringBuilder());
            return paths;
        }

        private void dfs(TreeNode root, List<String> paths, StringBuilder sb) {
            if (root == null) {
                return;
            }
            if (root.left == null && root.right == null) {
                sb.append(root.val);
                paths.add(sb.toString());
                return;
            }
            sb.append(root.val + "->");
            String curr = sb.toString();
            if (root.left != null) {
                dfs(root.left, paths, sb);
            }
            sb.setLength(0);
            sb.append(curr);
            if (root.right != null) {
                dfs(root.right, paths, sb);
            }
        }
    }

    public static class Solution3 {
        public List<String> binaryTreePaths_more_concise(TreeNode root) {
            List<String> result = new ArrayList<>();
            if (root == null) {
                return result;
            }
            dfs(root, new Stack<>(), result);
            return result;
        }

        private void dfs(TreeNode root, Stack<TreeNode> stack, List<String> result) {
            if (root == null) {
                return;
            }
            stack.push(root);
            dfs(root.left, stack, result);
            dfs(root.right,stack, result);
            if (root.left == null && root.right == null){
                result.add(String.join("->",  stack.stream().map(node -> "" +node.val).collect(Collectors.toList())));
            }
            stack.pop();
        }
    }

    public static void main(String[] args) {

        TreeNode root = TreeUtils.constructBinaryTree(Arrays.asList(1,2,3,4,5,6,7,8,9));
        TreeUtils.printBinaryTree(root);

        Solution3 solution = new Solution3();
        System.out.println(solution.binaryTreePaths_more_concise(root));
    }
}
