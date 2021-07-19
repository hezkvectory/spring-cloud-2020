package com.fishercoder;

import com.fishercoder.solutions._930;
import org.junit.BeforeClass;
import org.junit.Test;

public class _930Test {
    private static _930.Solution1 solution1;

    @BeforeClass
    public static void setup() {
        solution1 = new _930.Solution1();
    }

    @Test
    public void test1() {
        int[] nums = {1, 0, 1, 0, 1};
        int[] nums1 = {0, 0, 0, 0, 0};
        int goal = 2;
        int goal1 = 0;
        System.out.println(solution1.numSubarraysWithSum(nums1, goal1));
    }

}
