package structure.stack;


import common.exception.EmptyStackException;

/**
 * Created by boyce on 2014/7/16.
 */
public class IArrayStack<T> implements IStack<T> {
    private static final int DEFAULT_CAPACITY = 10;

    private Object[] stack;
    private int size;

    public IArrayStack() {
        this.stack = new Object[DEFAULT_CAPACITY];
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void push(T t) {
        if (this.size == this.stack.length)
            this.ensureCapacity();

        this.stack[this.size ++] = t;
    }

    public T peek() {
        if (this.size == 0)
            throw new EmptyStackException();

        return (T)this.stack[this.size-1];
    }

    public T pop() {
        T t = this.peek();

        this.stack[(this.size --) -1] = null;
        return t;
    }

    public void clear() {
        this.size = 0;
        this.stack = new Object[DEFAULT_CAPACITY];
    }

    private void ensureCapacity() {
        Object[] newStack = new Object[this.stack.length * 2];
        for (int i=0; i<this.stack.length; i++)
            newStack[i] = this.stack[i];

        this.stack = newStack;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        for (int i=0; i<this.size; i++) {
            builder.append(this.stack[i]).append(", ");
        }

        if (builder.length() > 2)
            builder.delete(builder.length()-2, builder.length());

        return builder.append("]").toString();

    }

    public static void main(String[] args) {
        IStack stack = new IArrayStack();
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
