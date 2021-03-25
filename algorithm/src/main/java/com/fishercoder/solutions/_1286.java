package com.fishercoder.solutions;

import java.util.ArrayList;
import java.util.List;

/**
 * 1286. Iterator for Combination
 * <p>
 * Design an Iterator class, which has:
 * A constructor that takes a string characters of sorted distinct lowercase English letters and a number combinationLength as arguments.
 * A function next() that returns the next combination of length combinationLength in lexicographical order.
 * A function hasNext() that returns True if and only if there exists a next combination.
 * <p>
 * Example:
 * CombinationIterator iterator = new CombinationIterator("abc", 2); // creates the iterator.
 * iterator.next(); // returns "ab"
 * iterator.hasNext(); // returns true
 * iterator.next(); // returns "ac"
 * iterator.hasNext(); // returns true
 * iterator.next(); // returns "bc"
 * iterator.hasNext(); // returns false
 * <p>
 * Constraints:
 * 1 <= combinationLength <= characters.length <= 15
 * There will be at most 10^4 function calls per test.
 * It's guaranteed that all calls of the function next are valid.
 */
public class _1286 {

    public static void main(String[] args) {
//        StringBuilder builder = new StringBuilder("1234");
//        System.out.println(builder.toString());
//        builder.setLength(builder.length() - 2);
//        System.out.println(builder.toString());


        Solution1.CombinationIterator iterator = new Solution1.CombinationIterator("abc", 2);
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    public static class Solution1 {
        public static class CombinationIterator {

            List<String> list;
            int index;
            int combinationLength;
            boolean[] visited;

            public CombinationIterator(String characters, int combinationLength) {
                this.index = 0;
                this.list = new ArrayList<>();
                this.combinationLength = combinationLength;
                this.visited = new boolean[characters.length()];
                buildAllCombinations(characters, 0, new StringBuilder(), visited);
            }

            private void buildAllCombinations(String characters, int start, StringBuilder sb, boolean[] visited) {
                if (sb.length() == combinationLength) {
                    list.add(sb.toString());
                    return;
                } else {
                    for (int i = start; i < characters.length(); i++) {
                        if (!visited[i]) {
                            sb.append(characters.charAt(i));
                            visited[i] = true;
                            buildAllCombinations(characters, i, sb, visited);
                            visited[i] = false;
                            sb.setLength(sb.length() - 1);
                        }
                    }

//                    for (int i = start; i < characters.length(); ) {
//                        if (!visited[i]) {
//                            sb.append(characters.charAt(i));
//                            visited[i] = true;
//                            buildAllCombinations(characters, i++, sb, visited);
//                            visited[i-1] = false;
//                            sb.setLength(sb.length() - 1);
//                        } else {
//                            i++;
//                        }
//                    }
                }
            }

            public String next() {
                return list.get(index++);
            }

            public boolean hasNext() {
                return index < list.size();
            }
        }
    }



    static class Solution {

        public static List<String> traverse(String str, int size) {
            List<String> result = new ArrayList<>();
            if (str == null || "".equals(str)) {
                return result;
            }
            boolean[] visited = new boolean[str.length()];
            dfs(result, new StringBuilder(), str, 0, size, visited);
            return result;
        }

        private static void dfs(List<String> result, StringBuilder temp, String str, int index, int size, boolean[] visited) {
            if (temp.length() == size) {
                result.add(temp.toString());
                return;
            }
            for (int i = index; i < str.length(); i++) {
                char c = str.charAt(i);
                if (i >= index && temp.indexOf(String.valueOf(c)) < 0) {
                    temp.append(c);
                    dfs(result, temp, str, i, size, visited);
                    temp.setLength(temp.length() - 1);
                }
            }
        }
    }
}
