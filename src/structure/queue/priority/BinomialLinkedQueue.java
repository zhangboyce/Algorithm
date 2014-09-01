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

    public BinomialLinkedQueue() {
    }

    public BinomialLinkedQueue(BinomialLinkedQueue<T> queue) {
        if (null == queue && !queue.isEmpty())
            this.merge(this.root, queue.root);
    }

    @Override
    public void offer(T t) {
        this.insert(new BinomialNode<T>(t));
    }

    private void insert(BinomialNode<T> node) {
        this.size += (1 << node.degree);
        this.root = this.merge(this.root, node);
    }

    //merge b1, b2
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
        BinomialNode prev = null;
        while (current != null && b2.degree > current.degree) {
            prev = current;
            current = current.next;
        }

        //end queue
        if (current == null)
            prev.next = b2;

        else if (b2.degree == current.degree) {

            //add new combine node into queue
            BinomialNode _new = this.combineTrees(current, b2);
            _new.next = current.next;
            current = _new;

            //head
            if (prev == null)
                b1 = _new;
            else
                prev.next = _new;

            //combine two adjacent node if degree equals
            while (current.next != null &&
                    current.next.degree == current.degree) {

                //add new combine node into queue
                _new = this.combineTrees(current, current.next);

                //remove current, current next and add _new
                _new.next = current.next.next;
                if (prev == null)
                    b1 = _new;
                else
                    prev.next = _new;

                current = _new;
            }

        //b2.degree < current.degree, add b2 prev current
        } else  {
            if (prev == null) {
                b2.next = current;
                b1 = b2;
            } else {
                prev.next = b2;
                b2.next = current;
            }
        }

        return b1;
    }

    @Override
    public T poll() {

        //find and remove min from queue
        if (this.isEmpty())
            throw new EmptyQueueException();

        BinomialNode<T> current = this.root;
        BinomialNode<T> prev = null;
        BinomialNode<T> min = current;
        BinomialNode<T> minPrev = prev;

        //find
        while (current.next != null) {
            prev = current;
            current = current.next;
            if (current.element.compareTo(min.element) < 0) {
                min = current;
                minPrev = prev;
            }
        }

        //remove
        if (null == minPrev)
            this.root = min.next;
        else
            minPrev.next = min.next;
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
        if (this.isEmpty())
            throw new EmptyQueueException();

        BinomialNode<T> current = this.root;
        BinomialNode<T> min = current;
        while (current.next != null) {
            current = current.next;
            if (current.element.compareTo(min.element) < 0)
                min = current;
        }

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
        throw new UnsupportedOperationException("Unsupported method.");
    }

    @Override
    public boolean contains(T t) {
        if (this.isEmpty()) return false;

        Iterator<BinomialNode> iterator = this.root.iterator();
        while (iterator.hasNext()) {
            if (this.contains(t, iterator.next()))
                return true;
        }
        return false;
    }

    private boolean contains(T t, BinomialNode node) {
        if (node == null) return false;

        if (node.element.equals(t))
            return true;

        return this.contains(t, node.leftNode) || this.contains(t, node.nextSibling);
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
        if (isEmpty()) return;

        Iterator<BinomialNode> iterator = this.root.iterator();
        while (iterator.hasNext()) {
            TreePrinter.printNode(iterator.next());
            System.out.println();
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

        // add a sub binomial node,
        private BinomialNode addSubBinomialNode(BinomialNode b) {
            b.nextSibling = this.leftNode;
            this.leftNode = b;

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
               throw new UnsupportedOperationException("");
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


        //queue.display();

        System.out.println("-------------size: " + queue.size());
        System.out.println("peek: " + queue.peek());
        System.out.println("display: ");
        queue.display();

        System.out.println("poll: " + queue.poll());
        System.out.println("-------------size: " + queue.size());
        System.out.println("display: ");
        queue.display();

        System.out.println(queue.contains(7));
        System.out.println(queue.contains(17));
    }
}
