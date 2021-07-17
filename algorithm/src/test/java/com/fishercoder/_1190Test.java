package com.fishercoder;

import com.fishercoder.solutions._1190;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class _1190Test {
    private static _1190.Solution1 solution1;
    private static _1190.Solution solution;

    @BeforeClass
    public static void setup() {
        solution1 = new _1190.Solution1();
        solution = new _1190.Solution();
    }

    @Test
    public void test1() {
        assertEquals("dcba", solution.reverseParentheses("(abcd)"));
    }

    @Test
    public void test2() {
        assertEquals("iloveu", solution.reverseParentheses("(u(love)i)"));
    }

    @Test
    public void test3() {
        assertEquals("leetcode", solution.reverseParentheses("(ed(et(oc))el)"));
    }

    @Test
    public void test4() {
        assertEquals("apmnolkjihgfedcbq", solution.reverseParentheses("a(bcdefghijkl(mno)p)q"));
    }

}