package structure.queue.priority.dynamic;

import structure.queue.priority.ILinkedPriorityQueue;

/**
 * Created by boyce on 2014/8/14.
 * priority queue implemented linked list
 */
public class ILinkedDynamicPriorityQueue
        extends ILinkedPriorityQueue<IDynamicPriorityQueue.DynamicPriorityQueueElement> implements IDynamicPriorityQueue {

    @Override
    public void decrease(int i, Integer offset) {
        Node node = this.index(i);
        ((IDynamicPriorityQueue.DynamicPriorityQueueElement)node.data).decrease(offset);
    }

    @Override
    public void increase(int i, Integer offset) {
        Node node = this.index(i);
        ((IDynamicPriorityQueue.DynamicPriorityQueueElement)node.data).increase(offset);
    }

    @Override
    public void remove(int i) {
        Node node = this.index(i);
        this.remove(node);
    }

    @Override
    public <T> void offer(T element, int priority) {
        this.offer(new DynamicPriorityQueueElement(element, priority));
    }

    private Node index(int i) {
        if (i < 0 || i > this.size)
            throw new IllegalArgumentException("Illegal indexOf, " + i);

        Node p = this.front.next;
        int j = 0;
        while (p != this.rear && j < i) {
            p = p.next;
            j ++;
        }
        return p;
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
//        iPriorityQueue.increase(0, 4);
//        System.out.println(iPriorityQueue);
//        iPriorityQueue.decrease(2, 5);
//        System.out.println(iPriorityQueue);
//        iPriorityQueue.decrease(4, 4);
//        System.out.println(iPriorityQueue);
//
//        iPriorityQueue.remove(2);
//        iPriorityQueue.remove(0);
//        System.out.println(iPriorityQueue);
    }
}
