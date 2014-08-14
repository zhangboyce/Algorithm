package structure.heap;

import common.exception.EmptyQueueException;
import structure.queue.ILinkedQueue;

/**
 * Created by boyce on 2014/8/14.
 * priority queue implemented linked list
 */
public class ILinkedPriorityQueue<T extends Comparable>
        extends ILinkedQueue<T> implements IPriorityQueue<T> {

    /**
     * peek the minimum element
     * @return
     */
    @Override
    public T peek() {
        if (isEmpty())
            throw new EmptyQueueException();

        Node node = this.front.next;
        T min = (T)node.data;
        while ((node=node.next) != this.rear) {

            if (((T) node.data).compareTo(min) < 0)
                min = (T)node.data;
        }
        return min;
    }

    @Override
    public T poll() {
        if (isEmpty())
            throw new EmptyQueueException();

        Node node = this.front.next;
        Node removed = node;
        T min = (T)node.data;
        while ((node=node.next) != this.rear) {
            if (((T) node.data).compareTo(min) < 0) {
                removed = node;
                min = (T)node.data;
            }
        }

        removed.prior.next = removed.next;
        removed.next.prior = removed.prior;

        this.size --;

        return min;
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
