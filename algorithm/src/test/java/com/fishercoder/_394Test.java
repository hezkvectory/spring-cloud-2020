package com.fishercoder;

import com.fishercoder.solutions._394;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by varunu28 on 1/08/19.
 */

public class _394Test {
  private static _394.Solution1 test;
  private static _394.Solution solution;
  private static _394.Solution3 solution3;

  @BeforeClass
  public static void setUp() {
    test = new _394.Solution1();
    solution = new _394.Solution();
    solution3 = new _394.Solution3();
  }

  @Test
  public void test1() {
    assertEquals("aaabcbc", solution3.decodeString("3[a]2[bc]"));
  }

  @Test
  public void test2() {
    assertEquals("accaccacc", solution3.decodeString("3[a2[c]]"));
  }

  @Test
  public void test3() {
    assertEquals("abcabccdcdcdef", solution3.decodeString("2[abc]3[cd]ef"));
  }
}
