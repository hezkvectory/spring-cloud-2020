package com.hezk.collection;

import org.junit.Test;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQueueTest {

    @Test
    public void test1() {
//        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> b - a);
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.reverseOrder());
//        PriorityQueue<Integer> queue = new PriorityQueue<>();
        queue.add(3);
        queue.add(2);
        queue.add(4);
        queue.add(-5);
        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }
}
