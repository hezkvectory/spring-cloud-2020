package com.fishercoder.solutions;

import java.util.*;

public class _778 {

    public static void main(String[] args) {
        int[][] grid = {
                {0, 1, 2, 3, 4},
                {20, 23, 22, 21, 5},
                {12, 13, 14, 15, 16},
                {11, 17, 18, 19, 20},
                {10, 9, 8, 7, 6}};

        Solution solution = new Solution();
        System.out.println(solution.swimInWater(grid));

    }

    static class Solution {
        public int swimInWater(int[][] grid) {
            int N = grid.length;
            Set<Integer> seen = new HashSet();
            PriorityQueue<Integer> pq = new PriorityQueue<Integer>((k1, k2) -> grid[k1 / N][k1 % N] - grid[k2 / N][k2 % N]);
            pq.offer(0);
            int ans = 0;

            int[] dr = new int[]{1, -1, 0, 0}; //row
            int[] dc = new int[]{0, 0, 1, -1}; //column

            while (!pq.isEmpty()) {
                int k = pq.poll();
                int r = k / N, c = k % N;
                ans = Math.max(ans, grid[r][c]);
                if (r == N - 1 && c == N - 1) {
                    return ans;
                }

                for (int i = 0; i < 4; ++i) {
                    int cr = r + dr[i], cc = c + dc[i];
                    int ck = cr * N + cc;
                    if (0 <= cr && cr < N && 0 <= cc && cc < N && !seen.contains(ck)) {
                        pq.offer(ck);
                        seen.add(ck);

//                        System.out.print("当前所在点:["+r+","+c+"]"+"搜索方向:["+dr[i]+","+dc[i]+"]"+"搜索结果:["+cr+","+cc+"]"+"存入列队的点:"+ck+","+"存入列队的点对应的数组值:"+grid[cr][cc]+",");
//                        System.out.print("目前列队存储的全部点对应的值:[");
//                        for(Integer a:pq){
//                            System.out.print(grid[a/N][a%N]+",");
//                        }
//                        System.out.print("]");
//                        System.out.println();
                    }
                }
            }

            throw null;
        }
    }

    class Solution2 {
        public int swimInWater(int[][] grid) {
            int N = grid.length;
            int lo = grid[0][0], hi = N * N;
            while (lo < hi) {
                int mi = lo + (hi - lo) / 2;
                if (!possible(mi, grid)) {
                    lo = mi + 1;
                } else {
                    hi = mi;
                }
            }
            return lo;
        }

        public boolean possible(int T, int[][] grid) {
            int N = grid.length;
            Set<Integer> seen = new HashSet();
            seen.add(0);
            int[] dr = new int[]{1, -1, 0, 0};
            int[] dc = new int[]{0, 0, 1, -1};

            Stack<Integer> stack = new Stack();
            stack.add(0);

            while (!stack.empty()) {
                int k = stack.pop();
                int r = k / N, c = k % N;
                if (r == N - 1 && c == N - 1) return true;

                for (int i = 0; i < 4; ++i) {
                    int cr = r + dr[i], cc = c + dc[i];
                    int ck = cr * N + cc;
                    if (0 <= cr && cr < N && 0 <= cc && cc < N
                            && !seen.contains(ck) && grid[cr][cc] <= T) {
                        stack.add(ck);
                        seen.add(ck);
                    }
                }
            }

            return false;
        }
    }

}
