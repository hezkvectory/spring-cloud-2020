package com.fishercoder.solutions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 17. Letter Combinations of a Phone Number
 *
 * Given a digit string, return all possible letter combinations that the number could represent.
 * A mapping of digit to letters (just like on the telephone buttons) is given below.

 Input:Digit string "23"
 Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].

 Note:
 Although the above answer is in lexicographical order, your answer could be in any order you want.
 */

public class _17 {

    public static void main(String[] args) {
        Solution1 solution1 = new Solution1();
        List<String> result = solution1.letterCombinations("569");
        System.out.println(result);
        System.out.println(result.size());
    }

    public static class Solution1 {
        public List<String> letterCombinations(String digits) {
            List<String> result = new ArrayList();
            if (digits.length() == 0) {
                return result;
            }

            String[] digits2Letters = new String[]{"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

            result.add("");//this line is important, otherwise result is empty and Java will default it to an empty String
            for (int i = 0; i < digits.length(); i++) {
                result = combine(digits2Letters[digits.charAt(i) - '0'], result);
            }

            return result;
        }

        List<String> combine(String letters, List<String> result) {
            List<String> newResult = new ArrayList();

            for (int i = 0; i < letters.length(); i++) {
                //the order of the two for loops doesn't matter, you could swap them and it still works.
                for (String str : result) {
                    newResult.add(str + letters.charAt(i));
                }
            }
            return newResult;
        }
    }

    public static class Solution2 {

        public List<String> letterCombinations(String str) {
            List<String> result = new ArrayList<>();
            if (str == null || str.isEmpty()) {
                return  result;
            }
            Map<Character, String> data = new HashMap<>();
            data.put('2', "abc");
            data.put('3', "def");
            data.put('4', "ghi");
            data.put('5', "jkl");
            data.put('6', "mno");
            data.put('7', "pqrs");
            data.put('8', "tuv");
            data.put('9', "wxyz");

            combile(result, data, str, 0, new ArrayList<>());
            return result;
        }

        private void combile(List<String> result, Map<Character, String> data, String str, int index, ArrayList<Character> temp) {
            if (temp.size() == str.length()) {
                result.add(String.join("",temp.stream().map(c -> String.valueOf(c)).collect(Collectors.toList())));
                return;
            }
            Character digest = str.charAt(index);
            String res = data.get(digest);
            for(int i = 0;i<res.length();i++) {
                temp.add(Character.valueOf(res.charAt(i)));
                combile(result, data, str, index + 1, temp);
                temp.remove(temp.size()-1);
            }
        }
    }
}
