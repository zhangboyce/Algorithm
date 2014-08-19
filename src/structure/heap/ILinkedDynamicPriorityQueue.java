package structure.heap;

import common.exception.EmptyQueueException;
import structure.queue.ILinkedQueue;

/**
 * Created by boyce on 2014/8/14.
 * priority queue implemented linked list
 */
public class ILinkedDynamicPriorityQueue
        extends ILinkedPriorityQueue implements IDynamicPriorityQueue<Integer> {

    @Override
    public void decrease(int i, Integer offset) {
        Node node = this.index(i);
        node.data = (Integer)node.data - offset;
    }

    @Override
    public void increase(int i, Integer offset) {
        Node node = this.index(i);
        node.data = (Integer)node.data + offset;
    }

    @Override
    public void delete(int i) {
        Node node = this.index(i);
        this.remove(node);
    }

    private Node index(int i) {
        if (i < 0 || i > this.size)
            throw new IllegalArgumentException("Illegal index, " + i);

        Node p = this.front.next;
        int j = 0;
        while (p != this.rear && j < i) {
            p = p.next;
            j ++;
        }
        return p;
    }

    public static void main(String[] args) {
        IDynamicPriorityQueue iPriorityQueue = new ILinkedDynamicPriorityQueue();
        iPriorityQueue.offer(9);
        iPriorityQueue.offer(8);
        iPriorityQueue.offer(-1);
        iPriorityQueue.offer(5);
        iPriorityQueue.offer(2);

        System.out.println(iPriorityQueue);
        iPriorityQueue.increase(0, 4);
        System.out.println(iPriorityQueue);
        iPriorityQueue.decrease(2, 5);
        System.out.println(iPriorityQueue);
        iPriorityQueue.decrease(4, 4);
        System.out.println(iPriorityQueue);

        iPriorityQueue.delete(2);
        iPriorityQueue.delete(0);
        System.out.println(iPriorityQueue);
    }
}
