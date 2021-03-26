package com.hezk.algorithm;

import java.util.ArrayDeque;
import java.util.Deque;

public class SimplifyPah {

    public static class Solution {
        public String simplify(String path) {
            String[] paths = path.split("/");
            Deque<String> deque = new ArrayDeque<>();
            for (String p : paths) {
                if (".".equals(p) || "/".equals(p)) {
                    continue;
                } else if ("..".equals(p)) {
                    deque.removeLast();
                } else {
                    deque.addLast(p);
                }
            }
            StringBuilder builder = new StringBuilder();
            for (String str : deque) {
                builder.append(str).append("/");
            }
            return builder.toString();
        }
    }

    public static void main(String[] args) {
        String path = "/ddd/dds/.././";
        System.out.println(new Solution().simplify(path));
    }
}