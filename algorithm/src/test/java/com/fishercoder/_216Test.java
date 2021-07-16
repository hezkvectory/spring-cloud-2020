package com.fishercoder;

import com.fishercoder.common.utils.CommonUtils;
import com.fishercoder.solutions._216;
import org.junit.BeforeClass;
import org.junit.Test;

public class _216Test {
    private static _216.Solution solution;

    @BeforeClass
    public static void setup() {
        solution = new _216.Solution();
    }

    @Test
    public void test() {
        CommonUtils.printListList(solution.combinationSum3(3, 7));
        CommonUtils.printListList(solution.combinationSum3(3, 8));
        CommonUtils.printListList(solution.combinationSum3(3, 9));
    }
}
