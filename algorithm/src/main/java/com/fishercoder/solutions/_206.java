package com.fishercoder.solutions;

import com.fishercoder.common.classes.ListNode;

/**
 * 206. Reverse Linked List
 * <p>
 * Reverse a singly linked list.
 * <p>
 * Example:
 * Input: 1->2->3->4->5->NULL
 * Output: 5->4->3->2->1->NULL
 * <p>
 * Follow up:
 * A linked list can be reversed either iteratively or recursively. Could you implement both?
 */
public class _206 {

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        int i = 2;
        ListNode cur = head;
        while (i < 6) {
            ListNode tmp = new ListNode(i++);
            cur.next = tmp;
            cur = cur.next;
        }
        Solution1 solution1 = new Solution1();
        ListNode rev = solution1.reverseList(head);
        ListNode.printList(rev);
    }

    public static class Solution1 {
        public ListNode reverseList(ListNode head) {
            ListNode prev = null;
            ListNode curr = head;
            while (curr != null) {
                ListNode next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }
            return prev;
        }
    }

    public static class Solution2 {
        public ListNode reverseList(ListNode head) {
            return reverse(head, null);
        }

        ListNode reverse(ListNode head, ListNode newHead) {
            if (head == null) {
                return newHead;
            }
            ListNode next = head.next;
            head.next = newHead;
            newHead = head;
            head = next;
            return reverse(head, newHead);
        }
    }

}