package structure.heap;

import common.exception.EmptyQueueException;
import structure.queue.ILinkedQueue;

/**
 * Created by boyce on 2014/8/14.
 * priority queue implemented linked list
 */
public class ILinkedPriorityQueue
        extends ILinkedQueue<Integer> implements IPriorityQueue {

    /**
     * peek the minimum element
     * @return
     */
    @Override
    public Integer peek() {
        if (isEmpty())
            throw new EmptyQueueException();

        Node node = this.front.next;
        Integer min = (Integer)node.data;
        while ((node=node.next) != this.rear) {

            if (((Integer) node.data).compareTo(min) < 0)
                min = (Integer)node.data;
        }
        return min;
    }

    @Override
    public Integer poll() {
        if (isEmpty())
            throw new EmptyQueueException();

        Node node = this.front.next;
        Node removed = node;
        Integer min = (Integer)node.data;
        while ((node=node.next) != this.rear) {
            if (((Integer) node.data).compareTo(min) < 0) {
                removed = node;
                min = (Integer)node.data;
            }
        }

        removed.prior.next = removed.next;
        removed.next.prior = removed.prior;

        this.size --;

        return min;
    }

    @Override
    public void display() {
        System.out.println(this.toString());
    }

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
        IPriorityQueue iPriorityQueue = new ILinkedPriorityQueue();
        iPriorityQueue.offer(3);
        iPriorityQueue.offer(0);
        iPriorityQueue.offer(9);
        iPriorityQueue.offer(-1);
        iPriorityQueue.offer(2);

        System.out.println(iPriorityQueue.poll());
        System.out.println(iPriorityQueue);
    }
}
