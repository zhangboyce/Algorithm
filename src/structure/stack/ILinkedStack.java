package structure.stack;


import common.exception.EmptyStackException;

/**
 * Created by boyce on 2014/7/16.
 */
public class ILinkedStack<T> implements IStack<T> {

    private int size;
    private Node head;

    public ILinkedStack() {
        this.head = new Node(null);
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
    public void push(T t) {
        Node n = new Node(t);
        n.next = this.head.next;
        this.head.next = n;

        this.size ++;
    }

    @Override
    public T peek() {
        if (isEmpty())
            throw new EmptyStackException();

        return (T)this.head.next.data;
    }

    @Override
    public T pop() {
        T t = this.peek();

        this.head.next = this.head.next.next;
        this.size --;
        return t;
    }

    @Override
    public void clear() {
        this.size = 0;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        Node p = this.head;
        while (p.next != null) {
            p = p.next;
            builder.append(p.data).append(", ");
        }

        if (builder.length() > 2)
            builder.delete(builder.length()-2, builder.length());

        return builder.append("]").toString();
    }

    private static class Node<T> {
        private Node next;
        private T data;

        private Node(Node next, T data) {
            this.next = next;
            this.data = data;
        }

        private Node(T data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        IStack stack = new ILinkedStack();
        stack.push("1");
        stack.push("2");
        stack.push("3");
        stack.push("4");
        stack.push("5");
        stack.push("6");

        System.out.println(stack);
        System.out.println(stack.pop());
        System.out.println(stack);
        System.out.println(stack.peek());
    }
}
