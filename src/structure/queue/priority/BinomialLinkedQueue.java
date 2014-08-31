package structure.queue.priority;

import common.exception.EmptyQueueException;
import structure.queue.IQueue;
import structure.tree.ITree;
import structure.tree.TreePrinter;

import java.util.Iterator;

/**
 * Created by boyce on 2014/8/27.
 */
public class BinomialLinkedQueue<T extends Comparable> implements IPriorityQueue<T> {

    private BinomialNode<T> root;
    private int size;

    @Override
    public void offer(T t) {
        this.insert(new BinomialNode<T>(t));
    }

    private void insert(BinomialNode<T> node) {
        this.root = this.merge(this.root, node);
        this.size ++;
    }

    private BinomialNode<T> merge(BinomialNode node1, BinomialNode node2) {
        if (node1 == null)
            return node2;

        if (node2 == null)
            return node1;

        Iterator<BinomialNode> iterator = node2.iterator();
        while (iterator.hasNext())
            node1 = merge1(node1, iterator.next());

        return node1;
    }

    //merge b2 into b1, b2 is a single BinomialNode, b1 is a linked BinomialNode
    private BinomialNode<T> merge1(BinomialNode<T> b1, BinomialNode<T> b2) {

        BinomialNode current = b1;
        while (b2.degree > current.degree && (current = current.next) != null)
            ;

        BinomialNode<T> prev = current.prev;
        if (b2.degree == current.degree) {
            BinomialNode<T> node = this.combineTrees(current, b2);

            while (current.next != null &&
                    current.next.degree == node.degree) {

                current = current.next;
                //prev = current.prev;
                node = this.combineTrees(current, node);
            }

            //head
            if (prev == null) {
                node.next = current.next;
                    if (node.next != null)
                node.next.prev = node;

                node.prev = null;
                return node;
            } else {
                prev.next = node;
                node.prev = prev;

                node.next = current.next;
                if (node.next != null)
                    node.next.prev = node;

                return b1;
            }

        //b2.degree < current.degree
        } else {
            //head
            if (prev == null) {
                b2.next = current;
                current.prev = b2;
                b2.prev = null;

                return b2;

            } else {
                prev.next = b2;
                b2.prev = prev;
                current.prev = b2;
                b2.next = current;

                return b1;
            }
        }
    }

    @Override
    public T poll() {
        BinomialNode<T> min = this.getMin();

        //remove min from queue
        if (min.prev == null)
            this.root = min.next;

        else if (min.next == null)
            min.prev.next = null;

        else {
            min.prev.next = min.next;
            min.next.prev = min.prev;
        }

        this.size -= (1 << min.degree);

        BinomialNode<T> left = min.leftNode;
        while (left != null) {
            BinomialNode<T> newNode = new BinomialNode<T>(left.element);
            newNode.leftNode = left.leftNode;
            newNode.degree = left.degree;
            this.insert(newNode);
            left = left.nextSibling;
        }

        return min.element;
    }

    @Override
    public T peek() {
        BinomialNode<T> min = this.getMin();
        return min.element;
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
        Iterator<BinomialNode> iterator = new BinomialLinkedQueueIterator();
        while (iterator.hasNext()) {
            TreePrinter.printNode(iterator.next());
            System.out.println();
        }
    }

    // get the min node of queue
    private BinomialNode<T> getMin() {
        if (this.isEmpty())
            throw new EmptyQueueException();

        BinomialNode<T> node = this.root;
        BinomialNode<T> min = node;
        while (node.next != null) {
            node = node.next;
            if (node.element.compareTo(min.element) < 0)
                min = node;
        }

        return min;
    }

    //this iterator
    private class BinomialLinkedQueueIterator implements Iterator<BinomialNode> {

        private BinomialNode current = BinomialLinkedQueue.this.root;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public BinomialNode next() {
            BinomialNode node = current;
            current = current.next;
            return node;
        }

        @Override
        public void remove() {

        }
    }

    //merge single BinomialNode equal-degree
    private BinomialNode combineTrees(BinomialNode b1, BinomialNode b2) {
        if (b1.degree != b2.degree)
            throw new IllegalArgumentException("Cannot merge two BinomialNode not equals degree.");

        if (b1.element.compareTo(b2.element) > 0)
            return b2.addSubBinomialNode(b1);

        return b1.addSubBinomialNode(b2);
    }

    /**
     * binomial queue node
     * @param <T>
     */
    private static class BinomialNode<T extends Comparable> implements ITree.INode<T>,
            Iterable<BinomialNode> {

        private T element;
        private BinomialNode<T> leftNode;
        private BinomialNode<T> nextSibling; //rightNode
        private int degree;

        //a binomial node has a next node to construct a linked queue
        private BinomialNode<T> next;
        private BinomialNode<T> prev;

        // add a sub binomial node,
        private BinomialNode addSubBinomialNode(BinomialNode b) {
            b.nextSibling = this.leftNode;
            this.leftNode = b;
            b.next = null;
            b.prev = null;

            this.degree ++;

            return this;
        }

        //get this iterator
        @Override
        public Iterator<BinomialNode> iterator() {
            return new BinomialNodeIterator();
        }

        //binomial node iterator
        private class BinomialNodeIterator implements Iterator<BinomialNode> {

            private BinomialNode current = BinomialNode.this;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public BinomialNode next() {
                BinomialNode node = current;
                current = current.next;
                return node;
            }

            @Override
            public void remove() {

            }
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

    public static void main(String[] args) {

        IPriorityQueue<Integer> queue = new BinomialLinkedQueue<Integer>();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        queue.offer(5);
        queue.offer(6);
        queue.offer(7);
        queue.offer(8);
        queue.offer(9);
        queue.offer(10);
        queue.offer(11);
        queue.offer(12);
        queue.offer(13);
        queue.offer(14);

        queue.display();

        System.out.println("peek: " + queue.peek());
        queue.display();

        System.out.println("poll: " + queue.poll());
        queue.display();
    }
}
