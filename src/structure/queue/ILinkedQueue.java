package structure.queue;

import common.exception.EmptyQueueException;

/**
 * Created by boyce on 2014/7/16.
 */
public class ILinkedQueue<T> implements IQueue<T> {

    protected int size;
    protected Node front;
    protected Node rear;

    public ILinkedQueue() {
        this.front = new Node();
        this.rear = new Node();
        this.front.next = rear;
        this.rear.prior = front;
    }

    @Override
    public void offer(T t) {
        Node n = new Node(t);

        this.front.next.prior = n;
        n.prior = this.front;

        n.next = this.front.next;
        this.front.next = n;

        this.size ++;
    }

    @Override
    public T poll() {
        T t = this.peek();

        this.rear.prior.prior.next = this.rear;
        this.rear.prior = this.rear.prior.prior;

        this.size --;

        return t;
    }

    @Override
    public T peek() {
        if (isEmpty())
            throw new EmptyQueueException();

        return (T)this.rear.prior.data;
    }

    @Override
    public int indexOf(T t) {
        Node p = this.front.next;
        int j = 0;

        while (p != null && !p.data.equals(t)) {
            p = p.next;
            j ++;
        }

        if (p != null) return j;
        return -1;
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
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public void clear() {
        this.size = 0;
        this.front.next = rear;
        this.rear.prior = front;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        Node p = this.front;
        while (p.next != this.rear) {
            p = p.next;
            builder.append(p.data).append(", ");
        }

        if (builder.length() > 2)
            builder.delete(builder.length()-2, builder.length());

        return builder.append("]").toString();
    }

    public static class Node<T> {
        public Node next;
        public Node prior;
        public T data;

        protected Node(Node next, T data) {
            this.data = data;
            this.next = next;
        }

        protected Node(T data) {
            this(null, data);
        }

        protected Node() {
            this(null, null);
        }
    }

    public static void main(String[] args) {
        IQueue queue = new ILinkedQueue();
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
        System.out.println(queue.indexOf("4"));
        System.out.println(queue.indexOf("6"));
        System.out.println(queue.indexOf("3"));
    }
}
