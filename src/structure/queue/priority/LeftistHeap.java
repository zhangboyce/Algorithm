package structure.queue.priority;

import structure.queue.IQueue;
import structure.tree.ITree;
import structure.tree.TreePrinter;

/**
 * Created by boyce on 2014/8/27.
 */
public class LeftistHeap<T extends Comparable> implements IPriorityQueue<T> {

    private LeftistNode root;

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
        TreePrinter.printNode(root);
    }

    private static class LeftistNode<T extends Comparable> implements ITree.INode<T> {

        @Override
        public T getElement() {
            return null;
        }

        @Override
        public ITree.INode getRightNode() {
            return null;
        }

        @Override
        public ITree.INode getLeftNode() {
            return null;
        }
    }
}
