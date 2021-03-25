package com.fishercoder.solutions;

import com.fishercoder.common.classes.ListNode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 23. Merge k Sorted Lists
 * <p>
 * Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.
 * <p>
 * Example:
 * <p>
 * Input:
 * [
 * 1->4->5,
 * 1->3->4,
 * 2->6
 * ]
 * Output: 1->1->2->3->4->4->5->6
 */

public class _23 {

    public static void main(String[] args) {
        ListNode l1 = ListNode.createSinglyLinkedList(Arrays.asList(1, 5, 7, 9));
        ListNode l2 = ListNode.createSinglyLinkedList(Arrays.asList(1, 5, 6, 7, 8, 9));
        ListNode l3 = ListNode.createSinglyLinkedList(Arrays.asList(-5, -1, 3, 5, 6, 7, 8, 9));


        Solution3 solution = new Solution3();
        ListNode.printList(solution.mergeKLists(new ListNode[]{l1, l2, l3}));
    }


    public static class Solution1 {
        public ListNode mergeKLists(ListNode[] lists) {
            PriorityQueue<ListNode> heap = new PriorityQueue((Comparator<ListNode>) (o1, o2) -> o1.val - o2.val);

            for (ListNode node : lists) {
                if (node != null) {
                    heap.add(node);
                }
            }

            ListNode dummy = new ListNode(-1);
            ListNode temp = dummy;
            while (!heap.isEmpty()) {
                temp.next = heap.poll();
                temp = temp.next;
                if (temp.next != null) {
                    heap.add(temp.next);
                }
            }
            return dummy.next;
        }
    }

    public static class Solution2 {

        private ListNode mergeTwoList(ListNode l1, ListNode l2) {
            if (l1 == null) {
                return l2;
            }
            if (l2 == null) {
                return l1;
            }
            if (l1.val < l2.val) {
                l1.next = mergeTwoList(l1.next, l2);
                return l1;
            } else {
                l2.next = mergeTwoList(l1, l2.next);
                return l2;
            }
        }

        public ListNode mergeKLists(ListNode[] lists, int start, int end) {
            if (end - start < 0) {
                return null;
            }
            if (end - start == 0) {
                return lists[end];
            }
            int mid = start + (end - start) / 2;
            return mergeTwoList(mergeKLists(lists, start, mid), mergeKLists(lists, mid + 1, end));
        }
    }

    public static class Solution3 {
        private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
            if (l1 == null) {
                return l2;
            }
            if (l2 == null) {
                return l1;
            }
            if (l1.val < l2.val) {
                l1.next = mergeTwoLists(l1.next, l2);
                return l1;
            } else {
                l2.next = mergeTwoLists(l1, l2.next);
                return l2;
            }
        }

        public ListNode mergeKLists(ListNode[] lists) {
            if (lists == null || lists.length == 0) {
                return null;
            }
            ListNode[] dummyHeads = new ListNode[lists.length];
            for (int i = 0; i < lists.length; i++) {
                ListNode node = new ListNode(0);
                node.next = lists[i];
                dummyHeads[i] = node;
            }

            // 自底向上进行merge
            for (int size = 1; size < lists.length; size += size) {
                for (int i = 0; i + size < lists.length; i += 2 * size) {
                    dummyHeads[i].next = mergeTwoLists(dummyHeads[i].next, dummyHeads[i + size].next);
                }
            }
            return dummyHeads[0].next;
        }
    }
}
