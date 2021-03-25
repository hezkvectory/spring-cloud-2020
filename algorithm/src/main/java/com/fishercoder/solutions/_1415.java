package com.fishercoder.solutions;

import java.util.ArrayList;
import java.util.List;

/**
 * 长度为n的开心数字
 * <p>
 * 解题思路
 * 执行用时 :1 ms, 在所有 Java 提交中击败了100.00%的用户
 * 内存消耗 :37.2 MB, 在所有 Java 提交中击败了100.00%的用户
 * 时间复杂度 O(n)
 * 空间复杂度 O(n)
 * <p>
 * 由于只有三个字符，而且相邻不能相等，所以最终就是三棵满二叉树，所以可以直接根据k定位到是具体数的位置，把这树的根节点到叶子节点的路径连起来就是最终结果
 * </p>
 */
public class _1415 {
    static class Solution {
        public String getHappyString(int n, int k) {
            //创建3个tri-tree，分别已'a','b','c'开头
            //由于总的字符只有三个，并且相邻两个不能相等，所以构建后的树是一个满二叉树，所以可以直接定义二叉树结构
            if (n <= 0) {
                return "";
            }
            int singleSize = (int) Math.pow(2, n - 1);
            if (k > 3 * singleSize) {
                return "";
            }
            //根据k的值直接决定位于哪颗树
            int remain;
            char[] result = new char[n];
            if (k > 2 * singleSize) {
                remain = k - 2 * singleSize;
                result[0] = 'c';
            } else if (k > singleSize) {
                remain = k - singleSize;
                result[0] = 'b';
            } else {
                remain = k;
                result[0] = 'a';
            }
            for (int i = 1; i < n; i++) {
                int half = (int) Math.pow(2, n - i - 1);
                char last = result[i - 1];
                if (remain <= half) {
                    if (last == 'a') {
                        result[i] = 'b';
                    } else if (last == 'b') {
                        result[i] = 'a';
                    } else {
                        result[i] = 'a';
                    }
                } else {
                    remain = remain - half;
                    if (last == 'a') {
                        result[i] = 'c';
                    } else if (last == 'b') {
                        result[i] = 'c';
                    } else {
                        result[i] = 'b';
                    }
                }
            }
            return new String(result);
        }
    }

    static class Solution2 {
        //方法一：回溯
        List<String> listres = new ArrayList<>();//记录长度为n的所有开心字符串

        public String getHappyString(int n, int k) {
            char[] num = {'a', 'b', 'c'};
            dfs(num, "", n);

            int len = listres.size();
            if (k > len) {
                return "";
            } else {
                return listres.get(k - 1);
            }
        }

        private void dfs(char[] num, String s, int n) {

            int len = s.length();
            if (len == n) {//如果为n则构成了一个完整的开心字符串，加入listres列表中
                listres.add(s);
                return;
            }

            for (int i = 0; i < num.length; i++) {
                if (s.equals("") || s.charAt(len - 1) != num[i]) {//如果是空串s s.charAt(len-1)越界
                    dfs(num, s + num[i], n);//像这种字符串的添加回溯的话，不用Stringbuilder，用 s + "" 就好 无需减了
                }
            }
        }
    }

}
