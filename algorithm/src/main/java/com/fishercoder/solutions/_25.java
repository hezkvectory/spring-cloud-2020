package com.fishercoder.solutions;

import com.fishercoder.common.classes.ListNode;

/**
 * 25. Reverse Nodes in k-Group
 * <p>
 * Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.
 * k is a positive integer and is less than or equal to the length of the linked list.
 * If the number of nodes is not a multiple of k then left-out nodes in the end should remain as it is.
 * You may not alter the values in the nodes, only nodes itself may be changed.
 * Only constant memory is allowed.
 * <p>
 * For example,
 * Given this linked list: 1->2->3->4->5
 * <p>
 * For k = 2, you should return: 2->1->4->3->5
 * <p>
 * For k = 3, you should return: 3->2->1->4->5
 */

public class _25 {

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

        Solution3 solution = new Solution3();
        ListNode.printList(solution.reverseKGroup(head, 3));
        System.out.println();
    }

    public static class Solution3 {
        public ListNode reverseKGroup(ListNode head, int k) {
            int count = 0;
            int i = 0;
            for (ListNode p = head; p != null; p = p.next) {
                if (p == null && i++ < k) {
                    return head;
                }
                count++;
            }
            int loopCount = count / k;
            ListNode dummyHead = new ListNode(0), p = dummyHead;
            dummyHead.next = head;
            for (i = 0; i < loopCount; i++) {
                ListNode pre = null, cur = p.next;
                for (int j = 0; j < k; j++) {
                    ListNode next = cur.next;
                    cur.next = pre;
                    pre = cur;
                    cur = next;
                }
                // 当前 pre 为该组的尾结点，cur 为下一组首节点
                ListNode start = p.next;// start 是该组首节点
                p.next = pre;
                start.next = cur;
                p = start;
            }
            return dummyHead.next;
        }
    }

    public static class Solution2 {
        public ListNode reverseKGroup(ListNode head, int k) {
            ListNode pre = null, cur = head;
            ListNode p = head;
            for (int i = 0; i < k; i++) {
                if (p == null) {
                    return head;
                }
                p = p.next;
            }
            for (int i = 0; i < k; i++) {
                ListNode next = cur.next;
                cur.next = pre;
                pre = cur;
                cur = next;
            }
            // pre为本组最后一个节点，cur为下一组的起点
            head.next = reverseKGroup(cur, k);
            return pre;
        }
    }

    public static class Solution1 {
        /**
         * We use recursion to go all the way until the end: when the number of nodes are smaller than k;
         * then we start to reverse each group of k nodes from the end towards the start.
         */
        public ListNode reverseKGroup(ListNode head, int k) {
            ListNode curr = head;
            int count = 0;
            while (curr != null && count != k) {
                //find the k+1 node
                curr = curr.next;
                count++;
            }

            if (count == k) {
                /**after this below recursive call finishes, it'll return head;
                 * then this returned "head" will become "curr", while the head
                 * in its previous callstack is the real head after this call.
                 * Setting up a break point will make all of this crystal clear.*/
                curr = reverseKGroup(curr, k);

                while (count-- > 0) {
                    ListNode temp = head.next;
                    head.next = curr;
                    curr = head;
                    head = temp;
                }
                head = curr;
            }
            return head;//we run out of nodes before we hit count == k, so we'll just directly return head in this case as well
        }

    }

}
