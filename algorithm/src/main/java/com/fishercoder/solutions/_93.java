package com.fishercoder.solutions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 93. Restore IP Addresses
 * <p>
 * Given a string containing only digits, restore it by returning all possible valid IP address
 * combinations.
 * <p>
 * For example: Given "25525511135",
 * <p>
 * return ["255.255.11.135", "255.255.111.35"]. (Order does not matter)
 */
public class _93 {
    public static void main(String[] args) {
//        Solution2 solution = new Solution2();
        Solution1 solution = new Solution1();
        System.out.println(solution.restoreIpAddresses("2525511135"));
    }

    public static class Solution1 {
        public List<String> restoreIpAddresses(String s) {
            List<String> allValidIpAddresses = new ArrayList<>();
            if (s == null || s.length() > 12 || s.length() < 4) {
                return allValidIpAddresses;
            }
            backtracking(s, new ArrayList<>(), allValidIpAddresses, 0);
            return allValidIpAddresses;
        }

        private void backtracking(String s, ArrayList<String> bytes, List<String> result, int pos) {
            if (bytes.size() == 4) {
                if (pos != s.length()) {
                    return;
                }
                result.add(String.join(".", bytes));
                return;
            }

            for (int i = pos; i < pos + 3 && i < s.length(); i++) {
                String oneByte = s.substring(pos, i + 1);
                if (!isValid(oneByte)) {
                    continue;
                }
                bytes.add(oneByte);
                backtracking(s, bytes, result, i + 1);
                bytes.remove(bytes.size() - 1);
            }
        }

        private boolean isValid(String oneByte) {
            if (oneByte.charAt(0) == '0') {
                return oneByte.equals("0");
            }
            int num = Integer.valueOf(oneByte);
            return (num >= 0 && num < 256);
        }
    }


    public static class Solution2 {
        public List<String> restoreIpAddresses(String str) {
            List<String> result = new ArrayList<>();
            if (str == null || str.length() < 4 || str.length() > 12) {
                return result;
            }
            dfs(str, 0, 0, new LinkedList<>(), result);
            return result;
        }

        private void dfs(String str, int nums, int start, LinkedList<String> strings, List<String> result) {
            if (strings.size() == 4) {
                if (str.length() == start) {
                    result.add(String.join(".", strings));
                }
                return;
            }
            if (str.length() - start < (4 - nums) || str.length() - start > (4 - nums) * 3) {
                return;
            }
            for (int i = 0; i < 3; i++) {
                if (start + i >= str.length()) {
                    break;
                }
                if (valid(str, start, start + i + 1)) {
                    strings.addLast(str.substring(start, start + i + 1));
                    dfs(str, nums + 1, start + i + 1, strings, result);
                    strings.removeLast();
                }
            }
        }

        private boolean valid(String str, int start, int end) {
            String tmp = str.substring(start, end);
            if (tmp.charAt(0) == '0') {
                return false;
            }
            int value = Integer.parseInt(tmp);
            return value >= 0 && value <= 255;
        }
    }
}
