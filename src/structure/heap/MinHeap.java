package structure.heap;

/**
 * Created by boyce on 2014/8/14.
 */
public class MinHeap<T extends Comparable> implements IPriorityQueue<T> {

    private final static int DEFAULT_CAPACITY = 10;

    private int current;
    private T[] elements;

    public MinHeap() {
        this(DEFAULT_CAPACITY);
    }

    public MinHeap(int capacity) {
        elements = (T[])new Comparable[capacity];
    }

    public MinHeap(T[] elements) {

    }

    @Override
    public void offer(T t) {

    }

    @Override
    public T poll() {
        return null;
    }

    @Override
    public T peek() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {

    }
}
