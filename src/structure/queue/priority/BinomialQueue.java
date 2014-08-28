package structure.queue.priority;

import structure.queue.IQueue;
import structure.tree.ITree;

/**
 * Created by boyce on 2014/8/27.
 */
public class BinomialQueue<T extends Comparable> implements IPriorityQueue<T> {

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

    @Override
    public int indexOf(T t) {
        return 0;
    }

    @Override
    public boolean contains(T t) {
        return false;
    }

    @Override
    public void addAll(IQueue<T> queue) {

    }

    @Override
    public void display() {
        
    }

    /**
     * binomial queue node
     * @param <T>
     */
    private static class BinomialNode<T extends Comparable> implements ITree.INode<T> {

        private T element;
        private BinomialNode<T> leftNode;
        private BinomialNode<T> nextSibling; //rightNode

        private BinomialNode(T element) {
            this.element = element;
        }

        @Override
        public T getElement() {
            return element;
        }

        @Override
        public BinomialNode<T> getRightNode() {
            return nextSibling;
        }

        @Override
        public BinomialNode<T> getLeftNode() {
            return leftNode;
        }
    }
}
