package structure.queue;

import common.exception.EmptyQueueException;

/**
 * Created by boyce on 2014/7/16.
 */
public class IArrayQueue<T> implements IQueue<T> {

    protected static final int DEFAULT_CAPACITY = 10;

    protected T[] data;
    protected int front;
    protected int rear;

    public IArrayQueue() {
        this(DEFAULT_CAPACITY);
    }

    public IArrayQueue(int capacity) {
        this.data = (T[])new Object[capacity];
    }

    @Override
    public void offer(T t) {
        //full
        if ((rear + 1) % data.length == front) {
            this.ensureCapacity();
        }

        data[rear] = t;
        rear = (rear + 1) % data.length;
    }

    @Override
    public T poll() {
        T t = this.peek();
        front = (front + 1) % data.length;
        return t;
    }

    @Override
    public T peek() {
        if (isEmpty())
            throw new EmptyQueueException();

        return data[front];
    }

    @Override
    public int indexOf(T t) {
        if (this.isEmpty())
            throw new EmptyQueueException();

        if (null == t) return -1;

        int index = -1;
        for (int i=front; i!=rear; i++) {
            i = (i + this.data.length) % this.data.length;
            if (this.data[i].equals(t)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public boolean contains(T t) {
        return -1 != this.indexOf(t);
    }

    @Override
    public void addAll(IQueue<T> queue) {
        if (null == queue || queue.isEmpty())
            return;

        while (!queue.isEmpty())
            this.offer(queue.poll());
    }

    @Override
    public int size() {
        return (rear - front + data.length) % data.length;
    }

    @Override
    public boolean isEmpty() {
        return this.front == this.rear;
    }

    @Override
    public void clear() {
        this.front = this.rear = 0;
        this.data = (T[])new Object[DEFAULT_CAPACITY];
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");

        for (int i=front; i != rear; i = (i+1) % data.length)
            builder.append(this.data[i].toString()).append(", ");

        if (builder.length() > 2)
            builder.delete(builder.length()-2, builder.length());
        builder.append("]");

        return builder.toString();
    }

    private void ensureCapacity() {
        T[] newQueue = (T[])new Object[this.data.length * 2];

        for (int i=front; i != rear; i = (i+1) % data.length)
            newQueue[i] = this.data[i];

        if (this.rear < this.front) {
            this.rear = this.front + this.size();
        }

        this.data = newQueue;
    }

    public static void main(String[] args) {
        IQueue<String> queue = new IArrayQueue(2);
        queue.offer("1");
        queue.offer("2");
        queue.offer("3");



        System.out.println(queue);
        System.out.println(queue.peek());
        System.out.println(queue.poll());
        System.out.println(queue);

        queue.offer("4");
        queue.offer("5");
        queue.offer("6");

        System.out.println(queue);
        System.out.println(queue.peek());
        System.out.println(queue.poll());
        System.out.println(queue);

        System.out.println(queue.indexOf("3"));
        System.out.println(queue.indexOf("5"));
        System.out.println(queue.indexOf("6"));
        System.out.println(queue.indexOf("7"));

        IQueue<String> queue2 = new IArrayQueue(2);
        queue2.offer("a");
        queue2.offer("b");
        queue2.offer("c");

        queue.addAll(queue2);
        System.out.println(queue);
        System.out.println(queue.peek());
    }
}
