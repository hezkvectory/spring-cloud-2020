package com.fishercoder.solutions;

import com.fishercoder.common.classes.ListNode;

/**
 * 61. Rotate List
 * <p>
 * Given a list, rotate_naive the list to the right by k places, where k is non-negative.
 * <p>
 * For example:
 * Given 1->2->3->4->5->NULL and k = 2,
 * return 4->5->1->2->3->NULL.
 */
public class _61 {

    public static class Solution1 {
        //credit: https://discuss.leetcode.com/topic/26364/clean-java-solution-with-brief-explanation
        //link the tail of the linked list to the head to form a circle, then count to find the pint and cut it
        public ListNode rotateRight(ListNode head, int k) {
            if (head == null) {
                return head;
            }
            ListNode copyHead = head;
            int len = 1;
            while (copyHead.next != null) {
                copyHead = copyHead.next;
                len++;
            }
            if (k % len == 0) {
                return head;
            }
            copyHead.next = head;//link the tail and head to make it a circle
            for (int i = len - k % len; i > 1; i--) {
                head = head.next;
            }
            copyHead = head.next;
            head.next = null;//break the circle
            return copyHead;
        }
    }


    public static class Solution {
        public static ListNode rotateRight(ListNode head, int k) {
            if (head == null) {
                return head;
            }
            int count = 1;
            ListNode dummyHead = head;
            while (head.next != null) {
                count++;
                head = head.next;
            }
            if (count % k == 0) {
                return dummyHead;
            }
            head.next = dummyHead;
            int rotate = k % count;
            while (rotate > 0) {
                dummyHead = dummyHead.next;
                rotate--;
            }
            head = dummyHead.next;
            dummyHead.next = null;
            return head;
        }


    }


}
