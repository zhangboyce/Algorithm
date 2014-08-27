package structure.queue.priority;

import structure.queue.IQueue;
import structure.tree.ITree;
import structure.tree.TreePrinter;

/**
 * Created by boyce on 2014/8/27.
 */
public abstract class DForkHeap<T extends Comparable> implements IPriorityQueue<T> {

    protected final static int DEFAULT_CAPACITY = 10;

    protected int currentSize;
    protected T[] elements;

    public DForkHeap() {
        this(DEFAULT_CAPACITY);
    }

    public DForkHeap(int capacity) {
        this.elements = (T[])new Comparable[capacity];
    }

    public DForkHeap(T[] elements) {
        this.elements = elements;
        this.currentSize = this.elements.length;

        this.buildHeap();
    }

    @Override
    public T peek() {
        return this.elements[0];
    }

    @Override
    public int size() {
        return this.currentSize;
    }

    @Override
    public boolean isEmpty() {
        return currentSize == 0;
    }

    @Override
    public void offer(T t) {
        if (currentSize == this.elements.length)
            this.enlargeCapacity(this.elements.length * 2);

        this.elements[currentSize] = t;
        this.trickleUp(currentSize ++);
    }

    @Override
    public T poll() {
        T element = this.peek();

        //remove root element, and set the last element as root
        this.elements[0] = this.elements[currentSize-1];

        //trickle down the root element to correct position
        this.trickleDown(0);
        currentSize --;

        return element;
    }

    @Override
    public void clear() {
        this.elements = (T[])new Comparable[DEFAULT_CAPACITY];
        this.currentSize = 0;
    }

    @Override
    public int indexOf(T t) {
        if (this.isEmpty()) return -1;

        for (int i=0; i<this.currentSize; i++) {
            if (this.elements[i].equals(t))
                return i;
        }
        return -1;
    }

    @Override
    public boolean contains(T t) {
        return -1 != this.indexOf(t);
    }

    @Override
    public void addAll(IQueue<T> queue) {
        if (null == queue || queue.isEmpty()) return;

        while (!queue.isEmpty())
            this.offer(queue.poll());
    }

    /**
     * abstract trickleUp method
     * @param i
     */
    protected abstract void trickleUp(int i);

    /**
     * abstract trickleDown method
     * @param i
     */
    protected abstract void trickleDown(int i);

    protected abstract void buildHeap();

    //enlarge the capacity
    private void enlargeCapacity(int capacity) {
        T[] newElements = (T[])new Comparable[capacity];
        for (int i=0; i<this.elements.length; i++)
            newElements[i] = this.elements[i];

        this.elements = newElements;
    }

}
