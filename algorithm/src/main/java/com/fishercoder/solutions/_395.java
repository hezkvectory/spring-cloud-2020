package com.fishercoder.solutions;

import java.util.HashMap;
import java.util.Map;

/**
 * 395. Longest Substring with At Least K Repeating Characters
 * <p>
 * Find the length of the longest substring T of a given string
 * (consists of lowercase letters only)
 * such that every character in T appears no less than k times.
 * <p>
 * Example 1:
 * Input:
 * s = "aaabb", k = 3
 * <p>
 * Output:
 * 3
 * <p>
 * The longest substring is "aaa", as 'a' is repeated 3 times.
 * <p>
 * <p>
 * Example 2:
 * Input:
 * s = "ababbc", k = 2
 * <p>
 * Output:
 * 5
 * <p>
 * The longest substring is "ababb", as 'a' is repeated 2 times and 'b' is repeated 3 times.
 */

public class _395 {

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.longestSubstring("ababb", 2));
    }

    public static class Solution {
        public int longestSubstring(String s, int k) {
            int len = s.length();
            if (len == 0 || k > len) {
                return 0;
            }
            if (k < 2) {
                return len;
            }
            return count(s.toCharArray(), k, 0, len - 1);
        }

        private int count(char[] chars, int k, int start, int end) {
            if (end - start + 1 < k) {
                return 0;
            }
            int[] times = new int[26];  //  26个字母
            //  统计出现频次
            for (int i = start; i <= end; ++i) {
                ++times[chars[i] - 'a'];
            }
            //  如果该字符出现频次小于k，则不可能出现在结果子串中
            //  分别排除，然后挪动两个指针
            while (end - start + 1 >= k && times[chars[start] - 'a'] < k) {
                ++start;
            }
            while (end - start + 1 >= k && times[chars[end] - 'a'] < k) {
                --end;
            }

            if (end - start + 1 < k) {
                return 0;
            }
            //  得到临时子串，再递归处理
            for (int i = start; i <= end; ++i) {
                //  如果第i个不符合要求，切分成左右两段分别递归求得
                if (times[chars[i] - 'a'] < k) {
                    return Math.max(count(chars, k, start, i - 1), count(chars, k, i + 1, end));
                }
            }
            return end - start + 1;
        }
    }

    static class Solution2 {
        public int longestSubstring(String s, int k) {
            if (s == null || s.length() == 0) {
                return 0;
            }
            // 使用数组存储字符出现的次数
            int[] hash = new int[26];
            for (int i = 0; i < s.length(); i++) {
                hash[s.charAt(i) - 'a']++;
            }
            // 是否整个字符串都符合要求
            boolean fullString = true;
            for (int i = 0; i < s.length(); i++) {
                // 若有字母小于 k 个，则说明整个字符串不符合，需要拆开来判断
                if (hash[s.charAt(i) - 'a'] > 0 && hash[s.charAt(i) - 'a'] < k) {
                    fullString = false;
                    break;
                }
            }
            if (fullString) {   // 整个字符串满足条件
                return s.length();
            }
            // 滑动窗口
            int left = 0;
            int right = 0;
            int max = 0;
            while (right < s.length()) {
                // 如果遇到 right 所指元素个数小于 k，则需要由此拆开来比较其他位置
                if (hash[s.charAt(right) - 'a'] < k) {
                    // aaabcccc
                    // l  r     左闭右开，所以取到的是 aaa
                    max = Math.max(max, longestSubstring(s.substring(left, right), k));
                    left = right + 1;
                }
                right++;
            }
            // aaabcccc
            //     l   r  即取到的是 cccc
            max = Math.max(max, longestSubstring(s.substring(left), k));
            return max;
        }

        public int longestSubstring0(String s, int k) {
            if (s == null || s.length() == 0) {
                return 0;
            }

            // 统计每个字符出现的次数
            Map<Character, Integer> map = new HashMap<>();
            for (char chs : s.toCharArray()) {
                map.put(chs, map.getOrDefault(chs, 0) + 1);
            }

            // 这里是先把 s 传进 sb 中来找出不满足 k 个的元素及其位置
            StringBuffer sb = new StringBuffer(s);
            for (int i = 0; i < s.length(); i++) {
                if (map.get(s.charAt(i)) < k) {
                    sb.setCharAt(i, ',');
                }
            }

            // 然后以不符合要求的元素位置进行分割存储到字符串数组中
            String[] strings = sb.toString().split(",");
            // 如果仅有一组，则说明只有一个字母，返回首字母即可
            if (strings.length == 1) {
                return strings[0].length();
            }

            // 有多组，则进行最大值比较
            int max = 0;
            for (String str : strings) {    // 递归求子串
                max = Math.max(max, longestSubstring(str, k));
            }

            return max;
        }
    }


    public static class Solution1 {
        /**
         * Reference: https://discuss.leetcode.com/topic/57372/java-divide-and-conquer-recursion-solution
         */
        public int longestSubstring(String s, int k) {
            return findLongestSubstring(s.toCharArray(), 0, s.length(), k);
        }

        int findLongestSubstring(char[] chars, int start, int end, int k) {
            /**Base case 1 of 2*/
            if (end - start < k) {
                return 0;
            }
            int[] count = new int[26];
            for (int i = start; i < end; i++) {
                int index = chars[i] - 'a';
                count[index]++;
            }

            /**For every character in the above frequency table*/
            for (int i = 0; i < 26; i++) {
                if (count[i] < k && count[i] > 0) {
                    for (int j = start; j < end; j++) {
                        if (chars[j] == i + 'a') {
                            int left = findLongestSubstring(chars, start, j, k);
                            int right = findLongestSubstring(chars, j + 1, end, k);
                            return Math.max(left, right);
                        }
                    }
                }
            }
            /**Base case 2 of 2:
             * when any characters in this substring has repeated at least k times, then this entire substring is a valid answer*/
            return end - start;
        }
    }
}
