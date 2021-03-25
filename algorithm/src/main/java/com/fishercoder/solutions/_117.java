package com.fishercoder.solutions;

import com.fishercoder.common.classes.TreeLinkNode;

/**
 * 117. Populating Next Right Pointers in Each Node II
 *
 * Follow up for problem "Populating Next Right Pointers in Each Node".
 * What if the given tree could be any binary tree? Would your previous solution still work?

 Note:

 You may only use constant extra space.
 For example,
 Given the following binary tree,
      1
    /  \
   2    3
  / \    \
 4   5    7
 After calling your function, the tree should look like:
      1 -> NULL
    /  \
   2 -> 3 -> NULL
  / \    \
 4-> 5 -> 7 -> NULL */

public class _117 {
    public static class Solution1 {
        /**credit: https://discuss.leetcode.com/topic/1106/o-1-space-o-n-complexity-iterative-solution
        O(1) space, based on level order traversal*/
        public void connect(TreeLinkNode root) {

            TreeLinkNode head = null; //head of the next level
            TreeLinkNode prev = null; //the leading node on the next level
            TreeLinkNode cur = root;  //current node of current level

            while (cur != null) {

                while (cur != null) { //iterate on the current level
                    //left child
                    if (cur.left != null) {
                        if (prev != null) {
                            prev.next = cur.left;
                        } else {
                            head = cur.left;
                        }
                        prev = cur.left;
                    }
                    //right child
                    if (cur.right != null) {
                        if (prev != null) {
                            prev.next = cur.right;
                        } else {
                            head = cur.right;
                        }
                        prev = cur.right;
                    }
                    //move to next node
                    cur = cur.next;
                }

                //move to next level
                cur = head;
                head = null;
                prev = null;
            }
        }
    }

    public static class Solution2 {
        TreeLinkNode last = null, nextStart = null;
        public void connect(TreeLinkNode root) {
            if (root == null) {
                return;
            }
            TreeLinkNode start = root;
            while(start != null) {
                last = null;
                nextStart = null;
                for(TreeLinkNode p = start; p != null; p = p.next) {
                    if (p.left != null) {
                        handler(p.left);
                    }
                    if (p.right != null) {
                        handler(p.right);
                    }
                }
                start = nextStart;
            }

        }
        private void handler(TreeLinkNode node) {
            if (last != null) {
                last.next = node;
            }
            last = node;
            if (nextStart == null) {
                nextStart = node;
            }
        }
    }

    public static void main(String[] args) {
        TreeLinkNode root = new TreeLinkNode(1);
        root.left = new TreeLinkNode(2);
        root.right = new TreeLinkNode(3);
        root.left.left = new TreeLinkNode(4);
        root.left.right = new TreeLinkNode(5);
        root.right.right = new TreeLinkNode(7);

        new Solution2().connect(root);
        System.out.println(root);
    }
}
