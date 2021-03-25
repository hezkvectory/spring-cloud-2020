package com.fishercoder.solutions;

import com.fishercoder.common.classes.ListNode;

/**
 * 24. Swap Nodes in Pairs
 * <p>
 * Given a linked list, swap every two adjacent nodes and return its head.
 * <p>
 * For example,
 * Given 1->2->3->4, you should return the list as 2->1->4->3.
 * <p>
 * Your algorithm should use only constant space. You may not modify the values in the list, only nodes itself can be changed.
 */
public class _24 {

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        int i = 2;
        ListNode cur = head;
        while (i < 10) {
            ListNode tmp = new ListNode(i++);
            cur.next = tmp;
            cur = cur.next;
        }
        ListNode.printList(head);

        Solution1 solution1 = new Solution1();
        Solution2 solution2 = new Solution2();

        ListNode.printList(solution2.swapPairs(head));
    }

    public static class Solution1 {
        public ListNode swapPairs(ListNode head) {
            if (head == null || head.next == null) {
                return head;
            }
            ListNode second = head.next;
            ListNode third = second.next;
            second.next = head;
            head.next = swapPairs(third);
            return second;
        }
    }

    public static class Solution2 {
        public ListNode swapPairs(ListNode head) {
            if (head == null || head.next == null)
                return head;
            ListNode dummy = new ListNode(0);
            ListNode p = dummy;
            dummy.next = head;
            ListNode node1, node2;
            while (((node1 = p.next) != null) && (node2 = p.next.next) != null) {
                node1.next = node2.next;
                node2.next = node1;
                p.next = node2;
                p = node1;
            }
            return dummy.next;
        }
    }
}
