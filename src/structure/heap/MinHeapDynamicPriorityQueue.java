package structure.heap;

import structure.tree.ITree;
import structure.tree.TreePrinter;

/**
 * Created by boyce on 2014/8/14.
 */
public class MinHeapDynamicPriorityQueue
        extends MinHeapPriorityQueue<Integer> implements IDynamicPriorityQueue {

    public MinHeapDynamicPriorityQueue() {
    }

    public MinHeapDynamicPriorityQueue(int capacity) {
        super(capacity);
    }

    public MinHeapDynamicPriorityQueue(Integer[] elements) {
        super(elements);
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

        Integer[] elements = {3, 4, 5, 1, 8, 9};
        IPriorityQueue iPriorityQueue = new MinHeapDynamicPriorityQueue(elements);
        iPriorityQueue.display();

        iPriorityQueue.offer(6);
        iPriorityQueue.offer(0);
        iPriorityQueue.offer(7);
        iPriorityQueue.offer(2);

        iPriorityQueue.display();
        iPriorityQueue.poll();
        iPriorityQueue.display();
    }
}
