package com.fishercoder;

import com.fishercoder.common.utils.CommonUtils;
import com.fishercoder.solutions._31;
import org.junit.BeforeClass;
import org.junit.Test;

public class _31Test {
    private static _31.Solution1 solution1;
    private static int[] nums;

    @BeforeClass
    public static void setup() {
        solution1 = new _31.Solution1();
    }

    @Test
    public void test1() {
        nums = new int[]{1, 2, 3};
        solution1.nextPermutation(nums);
        CommonUtils.printArray(nums);
    }

    @Test
    public void test2() {
        nums = new int[]{3, 5, 6, 2};
        solution1.nextPermutation(nums);
        CommonUtils.printArray(nums);
    }

    @Test
    public void test3() {
        nums = new int[]{3, 2, 6, 5, 4, 3, 2, 1};
        solution1.nextPermutation(nums);
        CommonUtils.printArray(nums);
    }
}
