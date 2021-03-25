package cn.itcast.algorithm.priority;

import java.util.Arrays;
import java.util.Comparator;

public class IndexPriorityQueue<T> {
    private int[] pq;
    private int[] qp;
    private Object[] element;
    private final int capacity;
    private int size;
    private Comparator<? super T> cmp;


    private static class Cmp<T> implements Comparator<T> {
        @SuppressWarnings({"unchecked", "rawtypes"})
        @Override
        public int compare(T t1, T t2) {
            return ((Comparable) (t1)).compareTo(t2);
        }
    }

    private static void swap(int[] a, int i, int j) {
        int tmp;
        tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    //与对象关联的整数范围是[0,capacity-1]
    public IndexPriorityQueue(int capacity, Comparator<T> cmp) {
        this.capacity = capacity;
        pq = new int[capacity + 1];
        qp = new int[capacity + 1];
        Arrays.fill(qp, -1);
        element = new Object[capacity + 1];
        if (cmp == null) {
            this.cmp = new Cmp<T>();
        }
    }

    public void enqueue(int k, T t) {
        k++;//使得关联的整数可以为0

        if (k > capacity) {
            throw new IllegalArgumentException();
        }

        if (qp[k] != -1) {
            element[k] = t;
            swim(qp[k]);
            sink(qp[k]);
            return;
        }

        size++;
        pq[size] = k;
        qp[k] = size;
        element[k] = t;

        swim(size);
    }

    @SuppressWarnings("unchecked")
    private void swim(int child) {
        int parent = child / 2;
        while (parent > 0) {
            if (cmp.compare((T) element[pq[child]], (T) element[pq[parent]]) < 0) {
                swap(pq, child, parent);
                swap(qp, pq[child], pq[parent]);
                child = parent;
                parent = child / 2;
            } else {
                break;
            }
        }
    }

    public int dequeue() {
        if (size == 0) {
            throw new IllegalArgumentException();
        }
        int r = pq[1];
        element[r] = null;
        swap(pq, size, 1);
        swap(qp, pq[size], pq[1]);
        pq[size] = -1;
        size--;
        sink(1);
        r--;//使得关联的整数可以为0
        return r;
    }

    @SuppressWarnings("unchecked")
    private void sink(int parent) {
        int child = parent * 2;
        while (child <= size) {
            if (child + 1 <= size) {
                int r = cmp.compare((T) element[pq[child]], (T) element[pq[child + 1]]);
                child = r > 0 ? child + 1 : child;
            }

            if (cmp.compare((T) element[pq[child]], (T) element[pq[parent]]) < 0) {
                swap(pq, parent, child);
                swap(qp, pq[parent], pq[child]);
                parent = child;
                child = parent * 2;
            } else {
                break;
            }
        }
    }

    public void change(int k, T t) {
        k++;
        if (qp[k] == -1) {
            throw new IllegalArgumentException();
        }
        element[k] = t;
        swim(qp[k]);
        sink(qp[k]);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public static void main(String[] args) {
        IndexPriorityQueue<String> ipq = new IndexPriorityQueue<String>(11, null);
        ipq.enqueue(0, "k");
        ipq.enqueue(6, "d");
        ipq.enqueue(3, "f");
        ipq.enqueue(4, "c");
        ipq.enqueue(0, "a");

        while (!ipq.isEmpty()) {
            System.out.println(ipq.dequeue());
        }
    }
}