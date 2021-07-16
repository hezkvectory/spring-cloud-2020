package com.fishercoder;

import com.fishercoder.common.utils.CommonUtils;
import com.fishercoder.solutions._46;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

public class _46Test {
    private static _46.Solution1 solution1;
    private static _46.Solution3 solution3;
    private static _46.Solution4 solution4;

    @BeforeClass
    public static void setup() {
        solution1 = new _46.Solution1();
        solution3 = new _46.Solution3();
        solution4 = new _46.Solution4();
    }

    @Test
    public void test1() {
        CommonUtils.printListList(solution1.permute(new int[]{1, 2, 3}));
    }

    @Test
    public void test2() {
        CommonUtils.printListList(solution3.permute(Arrays.asList(1, 2, 3)));
    }

    @Test
    public void test3() {
        CommonUtils.printListList(solution4.permute(Arrays.asList(1, 2, 3)));
    }
}
