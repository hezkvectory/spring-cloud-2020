package com.fishercoder;

import com.fishercoder.common.utils.CommonUtils;
import com.fishercoder.solutions._39;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class _39Test {
  private static _39.Solution1 solution1;
  private static _39.Solution solution;
  private static int[] candidates;
  private static List<List<Integer>> expected;

  @BeforeClass
  public static void setup() {
    solution1 = new _39.Solution1();
    solution = new _39.Solution();
  }

  @Test
  public void test1() {
    candidates = new int[] {2, 3, 6, 7};
    expected = new ArrayList<>();
    expected.add(Arrays.asList(2, 2, 3));
    expected.add(Arrays.asList(7));
    assertEquals(expected, solution1.combinationSum(candidates, 7));
  }

  @Test
  public void test(){
    candidates = new int[] {2, 3, 6, 7};
//    expected = new ArrayList<>();
//    expected.add(Arrays.asList(2, 2, 3));
//    expected.add(Arrays.asList(7));
//    assertEquals(expected, solution1.combinationSum(candidates, 7));
    CommonUtils.printListList(solution.combinationSum(candidates, 7));
  }
}
