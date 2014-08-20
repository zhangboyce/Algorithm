package structure.queue.priority.dynamic;

import common.exception.OutOfQueueException;
import structure.queue.priority.IArrayPriorityQueue;

/**
 * Created by boyce on 2014/8/14.
 * priority queue implemented array
 */
public class IArrayDynamicPriorityQueue
        extends IArrayPriorityQueue<Integer> implements IDynamicPriorityQueue {

    @Override
    public void decrease(int i, Integer offset) {
        int size1 = (rear - i + data.length)%data.length + (i - front + data.length)%data.length;
        if (size1 != this.size())
            throw new OutOfQueueException("the index out of queue, index: " + i);

        this.data[i] = (Integer)this.data[i] - offset;
        this.sort(front, i+1);
    }

    @Override
    public void increase(int i, Integer offset) {
        int size1 = (rear - i + data.length)%data.length + (i - front + data.length)%data.length;
        if (size1 != this.size())
            throw new OutOfQueueException("the index out of queue, index: " + i);

        this.data[i] = (Integer)this.data[i] + offset;
        this.sort(i, this.rear);
    }

    @Override
    public void delete(int i) {
        int size1 = (rear - i + data.length)%data.length + (i - front + data.length)%data.length;
        if (size1 != this.size())
            throw new OutOfQueueException("the index out of queue, index: " + i);

        for (; i != this.rear; i++) {
            this.data[i%this.data.length] = this.data[(i+1)%this.data.length];
        }
        this.rear = (this.rear + this.data.length - 1) % this.data.length;
    }

    public static void main(String[] args) {
        IDynamicPriorityQueue iPriorityQueue = new IArrayDynamicPriorityQueue();
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
