package structure.queue.priority;

import common.exception.EmptyQueueException;
import structure.queue.IQueue;
import structure.tree.ITree;

/**
 * Created by boyce on 2014/8/27.
 */
public class BinomialLinkedQueue<T extends Comparable> implements IPriorityQueue<T> {

    private BinomialTree root;
    private int size;

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
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public void clear() {
        this.size = 0;
        this.root = null;
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
        if (queue.isEmpty())
            throw new EmptyQueueException("Cannot access empty queue.");

        while (!queue.isEmpty())
            this.offer(queue.poll());
    }

    @Override
    public void display() {

    }

    /**
     * binomial queue tree
     * @param <T>
     */
    private static class BinomialTree<T extends Comparable> {
        private BinomialNode<T> root;

        private BinomialTree<T> merge(BinomialTree<T> tree) {
            this.root = this.combineTrees(this.root, tree.root);
            return this;
        }

        //merge two BinomialNode same size
        private BinomialNode combineTrees(BinomialNode b1, BinomialNode b2) {
            if (b1.element.compareTo(b2.element) > 0)
                return b2.addSubBinomialNode(b1);

            return b1.addSubBinomialNode(b2);
        }
    }

    /**
     * binomial queue node
     * @param <T>
     */
    private static class BinomialNode<T extends Comparable> implements ITree.INode<T> {

        private T element;
        private BinomialNode<T> leftNode;
        private BinomialNode<T> nextSibling; //rightNode
        private BinomialNode parent;
        private int degree;

        // add a sub binomial node,
        private BinomialNode addSubBinomialNode(BinomialNode b) {
            b.nextSibling = this.leftNode;
            this.leftNode = b;
            b.parent = this;

            this.degree ++;

            return this;
        }

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
