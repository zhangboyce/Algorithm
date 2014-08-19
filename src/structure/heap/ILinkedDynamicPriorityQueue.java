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

    }

    @Override
    public void increase(int i, Integer offset) {

    }

    @Override
    public void delete(int i) {

    }

    public static void main(String[] args) {
        IPriorityQueue iPriorityQueue = new ILinkedDynamicPriorityQueue();
        iPriorityQueue.offer(3);
        iPriorityQueue.offer(0);
        iPriorityQueue.offer(9);
        iPriorityQueue.offer(-1);
        iPriorityQueue.offer(2);

        System.out.println(iPriorityQueue.poll());
        System.out.println(iPriorityQueue);
    }
}
