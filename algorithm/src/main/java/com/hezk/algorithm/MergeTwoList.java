package com.hezk.algorithm;

import com.fishercoder.common.classes.ListNode;

import java.util.Arrays;
import java.util.List;

public class MergeTwoList {

    public static void main(String[] args) {
        ListNode l1 = ListNode.createSinglyLinkedList(Arrays.asList(1, 2, 3, 4, 5));
        ListNode l2 = ListNode.createSinglyLinkedList(Arrays.asList(1, 3, 5, 6));
        ListNode l3 = ListNode.createSinglyLinkedList(Arrays.asList(2, 5, 8));
        ListNode l4 = ListNode.createSinglyLinkedList(Arrays.asList(0, 3, 6));

//        Solution solution = new Solution();
//        ListNode ln = solution.mergeTwoList(l1, l2);
//        ListNode.printList(ln);

        Solution_1 solution = new Solution_1();
        ListNode ln = solution.mergeTwoList(l1, l2);
        ListNode.printList(ln);


//        Solution1 solution1 = new Solution1();
//        ListNode lnn = solution1.mergeManyList(Arrays.asList(l1, l2, l3, l4));
//        ListNode.printList(lnn);

        //0	1	1	2	2	3	3	3	4	5	5	5	6	6	8
        //  1	1	2	2	3	3	3	4	5	5	5	6	6	8

//        Solution2 solution2 = new Solution2();
//        ListNode lnn2 = solution2.mergeManyList(Arrays.asList(l1, l2, l3, l4));
//        ListNode.printList(lnn2);

    }

    public static class Solution {
        public ListNode mergeTwoList(ListNode l1, ListNode l2) {
            ListNode dummy = new ListNode(0);
            ListNode head = dummy;
            while (l1 != null && l2 != null) {
                if (l1.val < l2.val) {
                    head.next = l1;
                    l1 = l1.next;
                } else {
                    head.next = l2;
                    l2 = l2.next;
                }
                head = head.next;
            }
            head.next = l1 == null ? l2 : l1;
            return dummy.next;
        }
    }

    public static class Solution_1 {
        public ListNode mergeTwoList(ListNode l1, ListNode l2) {
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
    }

    public static class Solution1 {
        public ListNode mergeTwoList(ListNode l1, ListNode l2) {
            if (l1 == null || l2 == null) {
                return l1 == null ? l2 : l1;
            }
            ListNode dummy = new ListNode(0);
            ListNode head = dummy;
            while (l1 != null && l2 != null) {
                if (l1.val < l2.val) {
                    head.next = l1;
                    l1 = l1.next;
                } else {
                    head.next = l2;
                    l2 = l2.next;
                }
                head = head.next;
            }

            head.next = l1 == null ? l2 : l1;
            return dummy.next;
        }

        public ListNode mergeManyList(List<ListNode> container) {
            if (container == null || container.size() == 0) {
                return null;
            }
            ListNode dummy = null;
            for (int i = 0; i < container.size(); i++) {
                dummy = mergeTwoList(dummy, container.get(i));
            }
            return dummy;
        }
    }

    public static class Solution2 {
        private ListNode mergeTwoList(ListNode l1, ListNode l2) {
            if (l1 == null || l2 == null) {
                return l1 == null ? l2 : l1;
            }
            ListNode dummy = new ListNode(0);
            ListNode head = dummy;
            while (l1 != null && l2 != null) {
                if (l1.val < l2.val) {
                    head.next = l1;
                    l1 = l1.next;
                } else {
                    head.next = l2;
                    l2 = l2.next;
                }
                head = head.next;
            }

            head.next = l1 == null ? l2 : l1;
            return dummy.next;
        }

        private ListNode merge(List<ListNode> container, int left, int right) {
            if (left == right) {
                return container.get(left);
            }
            if (left > right) {
                return null;
            }
            int mid = (right + left) / 2;
            return mergeTwoList(merge(container, left, mid), merge(container, mid + 1, right));
        }

        public ListNode mergeManyList(List<ListNode> container) {
            return merge(container, 0, container.size() - 1);
        }
    }
}
