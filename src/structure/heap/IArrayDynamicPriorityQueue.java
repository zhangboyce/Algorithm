package structure.heap;

import common.exception.OutOfQueueException;

/**
 * Created by boyce on 2014/8/14.
 * priority queue implemented array
 */
public class IArrayDynamicPriorityQueue
        extends IArrayPriorityQueue implements IDynamicPriorityQueue<Integer> {

    @Override
    public void decrease(int i, Integer offset) {
        int size1 = (rear - i + data.length)%data.length + (i - front + data.length)%data.length;
        if (size1 != this.size())
            throw new OutOfQueueException("the index out of queue, index: " + i);

        this.data[i] = (Integer)this.data[i] - offset;
        this.sort(i, this.rear-1);
    }

    @Override
    public void increase(int i, Integer offset) {
        int size1 = (rear - i + data.length)%data.length + (i - front + data.length)%data.length;
        if (size1 != this.size())
            throw new OutOfQueueException("the index out of queue, index: " + i);

        this.data[i] = (Integer)this.data[i] + offset;
        this.sort(this.front, i);
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
        IPriorityQueue iPriorityQueue = new IArrayDynamicPriorityQueue();
        iPriorityQueue.offer(9);
        iPriorityQueue.offer(8);
        iPriorityQueue.offer(-1);
        iPriorityQueue.offer(5);
        iPriorityQueue.offer(2);

        System.out.println(iPriorityQueue.poll());
        System.out.println(iPriorityQueue);
    }

}
