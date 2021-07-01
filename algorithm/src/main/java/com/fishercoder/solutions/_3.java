package com.fishercoder.solutions;

import java.util.*;

/**
 * 3. Longest Substring Without Repeating Characters
 * <p>
 * Given a string, find the length of the longest substring without repeating characters.
 * <p>
 * Examples:
 * <p>
 * Given "abcabcbb", the answer is "abc", which the length is 3.
 * <p>
 * Given "bbbbb", the answer is "b", with the length of 1.
 * <p>
 * Given "pwwkew", the answer is "wke", with the length of 3.
 * <p>
 * Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
 */

public class _3 {

    public class Solution {
        public int lengthOfLongestSubstring(String s) {
            int n = s.length();
            int ans = 0;
            for (int i = 0; i < n; i++)
                for (int j = i + 1; j <= n; j++)
                    if (allUnique(s, i, j)) ans = Math.max(ans, j - i);
            return ans;
        }

        public boolean allUnique(String s, int start, int end) {
            Set<Character> set = new HashSet<>();
            for (int i = start; i < end; i++) {
                Character ch = s.charAt(i);
                if (set.contains(ch)) return false;
                set.add(ch);
            }
            return true;
        }
    }

    public static class Solution1 {
        public int lengthOfLongestSubstring(String s) {
            int result = 0;
            Map<Character, Integer> map = new HashMap();
            for (int i = 0, j = i; j < s.length(); ) {
                if (!map.containsKey(s.charAt(j)) || (map.containsKey(s.charAt(j)) && map.get(s.charAt(j)) == 0)) {
                    map.put(s.charAt(j), 1);
                    result = Math.max(j - i + 1, result);
                    j++;
                } else {
                    map.put(s.charAt(i), map.get(s.charAt(i)) - 1);
                    i++;
                }
            }
            return result;
        }
    }

    public static class Solution2 {
        public int lengthOfLongestSubstring(String s) {
            int n = s.length();
            Set<Character> set = new HashSet<>();
            int result = 0;
            int i = 0;
            int j = 0;
            while (i < n && j < n) {
                /**Try to extend the range i, j*/
                if (!set.contains(s.charAt(j))) {
                    set.add(s.charAt(j++));
                    result = Math.max(result, j - i);
                } else {
                    set.remove(s.charAt(i++));
                }
            }
            return result;
        }
    }

    public static class Solution3 {
        public int lengthOfLongestSubstring(String s) {
            if (s.length() == 0) {
                return 0;
            }
            int max = 0;
            Map<Character, Integer> map = new HashMap<>();
            for (int i = 0, j = 0; i < s.length(); i++) {
                if (map.containsKey(s.charAt(i))) {
                    j = Math.max(j, map.get(s.charAt(i)) + 1);
                }
                map.put(s.charAt(i), i);
                max = Math.max(max, i + 1 - j);
            }
            return max;
        }
    }

    public static class Solution4 {
        public int lengthOfLongestSubstring(String s) {
            if (s.length() == 0) {
                return 0;
            }
            int max = 0;
            int[] index = new int[128];
            for (int i = 0, j = 0; j < s.length(); j++) {
                i = Math.max(index[s.charAt(j)], i);
                max = Math.max(max, j - i + 1);
                index[s.charAt(j)] = j + 1;
            }
            return max;
        }
    }

    public static class Solution5 {
        public int lengthOfLongestSubstring(String s) {
            if (s.length() == 0) {
                return 0;
            }
            boolean[] used = new boolean[256];
            int max = 0;
            int left = 0, right = 0;
            int len = s.length();
            while (right < len) {
                if (used[s.charAt(right)]) {
                    max = Math.max(max, right - left);
                    while (left < right && s.charAt(left) != s.charAt(right)) {
                        used[s.charAt(left)] = false;
                        left++;
                    }
                    left++;
                    right++;
                } else {
                    used[s.charAt(right++)] = true;
                }
            }
            max = Math.max(max, right - left);
            return max;
        }
    }

    public static class Solution6 {

        public int lengthOfLongestSubstring(String str) {
            int len = str.length();

            int left = 0, right = 0;
            Map<Character, Integer> map = new HashMap();
            int maxLen = Integer.MIN_VALUE;

            while (right < len) {
                if (map.containsKey(str.charAt(right))) {
                    int pos = map.get(str.charAt(right));
                    Iterator<Map.Entry<Character, Integer>> iterator = map.entrySet().iterator();
                    while (iterator.hasNext()) {
                        if (pos > iterator.next().getValue()) {
                            iterator.remove();
                        }
                    }
                    maxLen = Math.max(maxLen, right - pos);
                }
                map.put(str.charAt(right), right);
                right++;
            }
            return maxLen;
        }

    }

    public static void main(String[] args) {
        Solution6 solution5 = new Solution6();
        System.out.println(solution5.lengthOfLongestSubstring("abcabcbb"));
        System.out.println(solution5.lengthOfLongestSubstring("bbbb"));
    }
}
