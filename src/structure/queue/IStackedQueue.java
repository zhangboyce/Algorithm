package structure.queue;

import common.exception.EmptyQueueException;
import structure.stack.ILinkedStack;
import structure.stack.IStack;

/**
 * Created by boyce on 2014/9/15.
 * a queue implements by two stack
 */
public class IStackedQueue<T> implements IQueue<T> {

    private IStack<T> offeredStack;
    private IStack<T> polledStack;

    public IStackedQueue() {
        offeredStack = new ILinkedStack<T>();
        polledStack = new ILinkedStack<T>();
    }

    @Override
    public void offer(T t) {
        if (null == t) return;
        offeredStack.push(t);
    }

    @Override
    public T poll() {
        if (this.isEmpty())
            throw new EmptyQueueException();

        if (polledStack.isEmpty()) {
            while (!offeredStack.isEmpty())
                polledStack.push(offeredStack.pop());
        }

        return polledStack.pop();
    }

    @Override
    public T peek() {
        if (this.isEmpty())
            throw new EmptyQueueException();

        if (polledStack.isEmpty()) {
            while (!offeredStack.isEmpty())
                polledStack.push(offeredStack.pop());
        }

        return polledStack.peek();
    }

    @Override
    public int size() {
        return offeredStack.size() + polledStack.size();
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public void clear() {
        offeredStack = new ILinkedStack<T>();
        polledStack = new ILinkedStack<T>();
    }

    @Override
    public int indexOf(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addAll(IQueue<T> queue) {
        if (null == queue || queue.isEmpty())
            return;

        while (!queue.isEmpty())
            this.offer(queue.poll());
    }
}
