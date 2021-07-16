package com.fishercoder;

import com.fishercoder.common.classes.ListNode;
import com.fishercoder.common.classes.TreeNode;
import com.fishercoder.common.utils.CommonUtils;
import com.fishercoder.common.utils.TreeUtils;
import com.fishercoder.solutions._145;
import com.fishercoder.solutions._147;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class _147Test {
    private static _147.Solution solution;
    private static ListNode head;

    @BeforeClass
    public static void setup() {
        solution = new _147.Solution();
        head = ListNode.createSinglyLinkedList(Arrays.asList(4, 2, 1, 3));
    }

    @Test
    public void test1() {
        CommonUtils.printList(solution.insertionSortList(head));
    }
}
