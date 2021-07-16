package com.fishercoder.solutions;

import java.util.ArrayList;
import java.util.List;

/**
 * 54. Spiral Matrix
 * <p>
 * Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral order.
 * <p>
 * For example,
 * Given the following matrix:
 * <p>
 * [
 * [ 1, 2, 3 ],
 * [ 4, 5, 6 ],
 * [ 7, 8, 9 ]
 * ]
 * <p>
 * You should return [1,2,3,6,9,8,7,4,5].
 */
public class _54 {

    public static class Solution1 {
        /**
         * credit: https://leetcode.com/problems/spiral-matrix/discuss/20599/Super-Simple-and-Easy-to-Understand-Solution/185257
         */
        public List<Integer> spiralOrder(int[][] matrix) {
            if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
                return new ArrayList<>();
            }
            int m = matrix.length;
            int n = matrix[0].length;
            List<Integer> result = new ArrayList();
            int left = 0;
            int right = n - 1;
            int top = 0;
            int bottom = m - 1;
            while (result.size() < m * n) {
                for (int j = left; j <= right && result.size() < m * n; j++) {
                    result.add(matrix[top][j]);
                }
                top++;
                for (int i = top; i <= bottom && result.size() < m * n; i++) {
                    result.add(matrix[i][right]);
                }
                right--;
                for (int j = right; j >= left && result.size() < m * n; j--) {
                    result.add(matrix[bottom][j]);
                }
                bottom--;
                for (int i = bottom; i >= top && result.size() < m * n; i--) {
                    result.add(matrix[i][left]);
                }
                left++;
            }
            return result;
        }
    }


    public static class Solution {
        public List<Integer> spiralOrder(int[][] matrix) {
            List<Integer> ans = new ArrayList<>();
            if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
                return ans;
            }
            int row = matrix.length;
            int col = matrix[0].length;
            int total = row * col;

            int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

            boolean[][] visited = new boolean[matrix.length + 1][matrix[0].length + 1];
            int direction = 0;
            int x = 0;
            int y = 0;
            for (int i = 0; i < total; i++) {
                ans.add(matrix[x][y]);
                visited[x][y] = true;
                int newRow = x + directions[direction][0];
                int newCol = y + directions[direction][1];
                if (newRow > row - 1 || 0 > newRow || newCol > col - 1 || 0 > newCol || visited[newRow][newCol]) {
                    direction = (direction + 1) % 4;
                }
                x += directions[direction][0];
                y += directions[direction][1];
            }
            return ans;
        }
    }


}
