package com.fishercoder;

import com.fishercoder.solutions._647;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class _647Test {
    private static _647.Solution1 solution1;
    private static _647.Solution solution;

    @BeforeClass
    public static void setup() {
        solution1 = new _647.Solution1();
        solution = new _647.Solution();
    }

    @Test
    public void test1() {
        assertEquals(3, solution.countSubstrings("abc"));
    }

}
