package structure.queue.priority.dynamic;

import common.exception.OutOfQueueException;
import structure.queue.priority.IArrayPriorityQueue;

/**
 * Created by boyce on 2014/8/14.
 * priority queue implemented array
 */
public class IArrayDynamicPriorityQueue
        extends IArrayPriorityQueue<IDynamicPriorityQueue.DynamicPriorityQueueElement> implements IDynamicPriorityQueue {

    public IArrayDynamicPriorityQueue() {
        this(DEFAULT_CAPACITY);
    }

    public IArrayDynamicPriorityQueue(int capacity) {
        this.data = new DynamicPriorityQueueElement[capacity];
    }

    @Override
    public void decrease(int i, Integer offset) {
        int size1 = (rear - i + data.length)%data.length + (i - front + data.length)%data.length;
        if (size1 != this.size())
            throw new OutOfQueueException("the indexOf out of queue, indexOf: " + i);

        this.data[i].decrease(offset);
        this.sort(front, i+1);
    }

    @Override
    public void increase(int i, Integer offset) {
        int size1 = (rear - i + data.length)%data.length + (i - front + data.length)%data.length;
        if (size1 != this.size())
            throw new OutOfQueueException("the indexOf out of queue, indexOf: " + i);

        this.data[i].increase(offset);
        this.sort(i, this.rear);
    }

    @Override
    public void remove(int i) {
        int size1 = (rear - i + data.length)%data.length + (i - front + data.length)%data.length;
        if (size1 != this.size())
            throw new OutOfQueueException("the indexOf out of queue, indexOf: " + i);

        for (; i != this.rear; i++) {
            this.data[i%this.data.length] = this.data[(i+1)%this.data.length];
        }
        this.rear = (this.rear + this.data.length - 1) % this.data.length;
    }

    @Override
    public <T> void offer(T element, int priority) {
        this.offer(new DynamicPriorityQueueElement(element, priority));
    }

    public static void main(String[] args) {
        IDynamicPriorityQueue iPriorityQueue = new IArrayDynamicPriorityQueue();
        iPriorityQueue.offer(new DynamicPriorityQueueElement(9));
        iPriorityQueue.offer(new DynamicPriorityQueueElement(8));
        iPriorityQueue.offer(new DynamicPriorityQueueElement(-1));
        iPriorityQueue.offer(new DynamicPriorityQueueElement(5));
        iPriorityQueue.offer(new DynamicPriorityQueueElement(2));
        iPriorityQueue.offer(new DynamicPriorityQueueElement(3));

        System.out.println(iPriorityQueue);
        iPriorityQueue.increase(0, 4);
        System.out.println(iPriorityQueue);
        iPriorityQueue.decrease(2, 5);
        System.out.println(iPriorityQueue);
        iPriorityQueue.decrease(4, 4);
        System.out.println(iPriorityQueue);

        iPriorityQueue.remove(2);
        iPriorityQueue.remove(0);
        System.out.println(iPriorityQueue);

    }

}
