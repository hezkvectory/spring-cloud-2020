package com.fishercoder.solutions;

/**
 * 79. Word Search
 * <p>
 * Given a 2D board and a word, find if the word exists in the grid.
 * The word can be constructed from letters of sequentially adjacent cell,
 * where "adjacent" cells are those horizontally or vertically neighboring.
 * The same letter cell may not be used more than once.
 * <p>
 * For example,
 * Given board =
 * [
 * ['A','B','C','E'],
 * ['S','F','C','S'],
 * ['A','D','E','E']
 * ]
 * <p>
 * word = "ABCCED", -> returns true,
 * word = "SEE", -> returns true,
 * word = "ABCB", -> returns false.
 */

public class _79 {

    public static class Solution1 {

        public boolean exist(char[][] board, String word) {
            int m = board.length;
            int n = board[0].length;
            boolean[][] visited = new boolean[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (dfs(board, visited, i, j, word, 0)) {
                        return true;
                    }
                }
            }
            return false;
        }

        final int[] dirs = new int[]{0, 1, 0, -1, 0};

        boolean dfs(char[][] board, boolean[][] visited, int row, int col, String word, int index) {
            if (index >= word.length() || word.charAt(index) != board[row][col]) {
                return false;
            } else if (index == word.length() - 1 && word.charAt(index) == board[row][col]) {
                visited[row][col] = true;
                return true;
            }
            visited[row][col] = true;//set it to true for this case
            boolean result = false;
            for (int i = 0; i < 4; i++) {
                int nextRow = row + dirs[i];
                int nextCol = col + dirs[i + 1];
                if (nextRow < 0 || nextRow >= board.length || nextCol < 0 || nextCol >= board[0].length || visited[nextRow][nextCol]) {
                    continue;
                }
                result = dfs(board, visited, nextRow, nextCol, word, index + 1);
                if (result) {
                    return result;
                } else {
                    visited[nextRow][nextCol] = false;//set it back to false if this road doesn't work to allow it for other paths, this is backtracking!!!
                }
            }
            return result;
        }
    }

    public static class Solution2 {
        //credit: https://discuss.leetcode.com/topic/21142/my-java-solution

        boolean[][] visited;

        public boolean exist(char[][] board, String word) {
            int m = board.length;
            int n = board[0].length;
            visited = new boolean[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (word.charAt(0) == board[i][j] && search(board, word, i, j, 0)) {
                        return true;
                    }
                }
            }
            return false;
        }

        boolean search(char[][] board, String word, int i, int j, int pos) {
            if (pos == word.length()) {
                return true;
            }
            if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || word.charAt(pos) != board[i][j] || visited[i][j]) {
                return false;
            }
            visited[i][j] = true;
            if (search(board, word, i + 1, j, pos + 1)
                    || search(board, word, i - 1, j, pos + 1)
                    || search(board, word, i, j + 1, pos + 1)
                    || search(board, word, i, j - 1, pos + 1)) {
                return true;
            }

            visited[i][j] = false;
            return false;
        }
    }

    static class Solution3 {
        public boolean exist(char[][] board, String word) {
            char[] words = word.toCharArray();
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    //从[i,j]这个坐标开始查找
                    if (dfs(board, words, i, j, 0))
                        return true;
                }
            }
            return false;
        }

        boolean dfs(char[][] board, char[] word, int i, int j, int index) {
            //边界的判断，如果越界直接返回false。index表示的是查找到字符串word的第几个字符，
            //如果这个字符不等于board[i][j]，说明验证这个坐标路径是走不通的，直接返回false
            if (i >= board.length || i < 0 || j >= board[0].length || j < 0 || board[i][j] != word[index])
                return false;
            //如果word的每个字符都查找完了，直接返回true
            if (index == word.length - 1)
                return true;
            //把当前坐标的值保存下来，为了在最后复原
            char tmp = board[i][j];
            //然后修改当前坐标的值
            board[i][j] = '.';
            //走递归，沿着当前坐标的上下左右4个方向查找
            boolean res = dfs(board, word, i + 1, j, index + 1) || dfs(board, word, i - 1, j, index + 1) ||
                    dfs(board, word, i, j + 1, index + 1) || dfs(board, word, i, j - 1, index + 1);
            //递归之后再把当前的坐标复原
            board[i][j] = tmp;
            return res;
        }
    }
}
