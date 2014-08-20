package structure.queue.priority.dynamic;

import structure.queue.priority.IPriorityQueue;
import structure.queue.priority.MinHeapPriorityQueue;
import structure.tree.ITree;
import structure.tree.TreePrinter;

/**
 * Created by boyce on 2014/8/14.
 */
public class MinHeapDynamicPriorityQueue
        extends MinHeapPriorityQueue<IDynamicPriorityQueue.DynamicPriorityQueueElement> implements IDynamicPriorityQueue {

    public MinHeapDynamicPriorityQueue() {
        this(DEFAULT_CAPACITY);
    }

    public MinHeapDynamicPriorityQueue(int capacity) {
        this.elements = new DynamicPriorityQueueElement[capacity];
    }

    public MinHeapDynamicPriorityQueue(DynamicPriorityQueueElement[] elements) {
        this.elements = elements;
        this.currentSize = this.elements.length;

        this.buildHeap();
    }

    @Override
    public void decrease(int i, Integer offset) {

    }

    @Override
    public void increase(int i, Integer offset) {

    }

    @Override
    public void remove(int i) {

    }

    @Override
    public <T> void offer(T element, int priority) {
        this.offer(new DynamicPriorityQueueElement(element, priority));
    }

    public static void main(String[] args) {

        DynamicPriorityQueueElement[] elements = {
                new DynamicPriorityQueueElement(3),
                new DynamicPriorityQueueElement(4),
                new DynamicPriorityQueueElement(5),
                new DynamicPriorityQueueElement(1),
                new DynamicPriorityQueueElement(8),
                new DynamicPriorityQueueElement(9) };
        IDynamicPriorityQueue iPriorityQueue = new MinHeapDynamicPriorityQueue(elements);
        iPriorityQueue.display();

    }
}
