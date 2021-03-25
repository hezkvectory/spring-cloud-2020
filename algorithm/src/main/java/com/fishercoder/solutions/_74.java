package com.fishercoder.solutions;

/**
 * 74. Search a 2D Matrix
 * <p>
 * Write an efficient algorithm that searches for a value in an m x n matrix.
 * This matrix has the following properties:
 * <p>
 * Integers in each row are sorted from left to right.
 * The first integer of each row is greater than the last integer of the previous row.
 * For example,
 * <p>
 * Consider the following matrix:
 * <p>
 * [
 * [1,   3,  5,  7],
 * [10, 11, 16, 20],
 * [23, 30, 34, 50]
 * ]
 * <p>
 * Given target = 3, return true.
 */
public class _74 {

    public static void main(String[] args) {
        int[][] grid = {
                {1, 3, 5, 7},
                {10, 11, 16, 20},
                {23, 30, 34, 50}
        };
        int target = 4;

        Solution solution = new Solution();
        int row = solution.getRow(grid, target);
        System.out.println(solution.getCol(grid[row], target));
    }

    static class Solution {

        public int getRow(int[][] grid, int target) {
            int min = 0, max = grid.length - 1;
            int col = grid[0].length - 1;
            while (min < max) {
                int mid = (min + max) / 2;
                if (grid[mid][col] < target) {
                    min = mid + 1;
                } else {
                    max = mid;
                }
            }
            return min;
        }

        public boolean getCol(int[] arr, int target) {
            int l = 0, r = arr.length - 1;
            while (l <= r) {
                int mid = (l + r) / 2;
                if (arr[mid] == target) {
                    return true;
                } else if (arr[mid] < target) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
            return false;
        }
    }

    public static class Solution1 {
        public boolean searchMatrix(int[][] matrix, int target) {
            if (matrix == null || matrix.length == 0
                    || matrix[0].length == 0
                    || matrix[0][0] > target
                    || matrix[matrix.length - 1][matrix[0].length - 1] < target) {
                return false;
            }
            int m = matrix.length;
            int n = matrix[0].length;
            int left = 0;
            int right = m * n - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                int row = mid / n;
                int col = mid % n;
                if (matrix[row][col] == target) {
                    return true;
                } else if (matrix[row][col] > target) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            return false;
        }
    }
}
