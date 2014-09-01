package structure.queue.priority;

import common.exception.EmptyQueueException;
import structure.queue.IQueue;
import structure.tree.ITree;
import structure.tree.TreePrinter;

import java.util.Iterator;

/**
 * Created by boyce on 2014/8/27.
 */
public class BinomialArrayQueue<T extends Comparable> implements IPriorityQueue<T> {

    private static final int DEFAULT_CAPACITY = 4;

    // binomial node array, sorted by degree
    private BinomialNode<T>[] queue;
    private int size;

    public BinomialArrayQueue() {
        this(DEFAULT_CAPACITY);
    }

    public BinomialArrayQueue(int capacity) {
        if (capacity < 0)
            throw new IllegalArgumentException();

        this.queue = new BinomialNode[capacity];
    }

    @Override
    public void offer(T t) {
        BinomialNode<T> node = new BinomialNode<T>(t);
        this.size += (1 << node.degree);
        this.insert(node);
    }

    @Override
    public T poll() {
        if (isEmpty())
            throw new EmptyQueueException();

        Iterator<BinomialNode> iterator = this.iterator();
        BinomialNode<T> min = null;
        BinomialNode<T> current;
        while (iterator.hasNext()) {
            current = iterator.next();
            if (min == null || current.element.compareTo(min.element) < 0)
                min = current;
        }

        //remove min
        this.size -= (1 << min.degree);
        this.queue[min.degree] = null;

        BinomialArrayQueue<T> children = new BinomialArrayQueue<T>(min.degree);
        BinomialNode<T> left = min.leftNode;
        while (left != null) {
            children.queue[left.degree] = left;
            children.size += (1 << left.degree);
            children.queue[left.degree].nextSibling = null;

            left = left.nextSibling;
        }

        this.merge(children);

        return min.element;
    }

    @Override
    public T peek() {
        if (isEmpty())
            throw new EmptyQueueException();

        Iterator<BinomialNode> iterator = this.iterator();
        BinomialNode<T> min = null;
        BinomialNode<T> current;
        while (iterator.hasNext()) {
            current = iterator.next();
            if (min == null || current.element.compareTo(min.element) < 0)
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
        this.queue = new BinomialNode[DEFAULT_CAPACITY];
    }

    @Override
    public int indexOf(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(T t) {
        if (this.isEmpty()) return false;

        Iterator<BinomialNode> iterator = this.iterator();
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
        Iterator<BinomialNode> iterator = this.iterator();
        while (iterator.hasNext()) {
            TreePrinter.printNode(iterator.next());
            System.out.println();
        }
    }

    //insert a binomial node to queue
    private void insert(BinomialNode<T> node) {
        if (node == null) return;

        int i = node.degree;
        if (i >= this.queue.length) {
            this.ensureCapacity(i + 1);
            this.queue[i] = node;
            return;
        }

        BinomialNode<T> b = this.queue[i];
        if (b == null) {
            this.queue[i] = node;
        } else {
            BinomialNode<T> carry = this.combineTrees(b, node);
            this.queue[i] = null;
            this.insert(carry);
        }
    }

    //merge a arrayed binomial node queue
    //like 101001110 + 110101 binary addition
    private void merge(BinomialArrayQueue<T> rhs) {
        if (this == rhs || null == rhs || rhs.isEmpty())
            return;

        this.size += rhs.size;
        if (this.size >= this.queue.length) {
            int maxSize = Math.max(this.queue.length, rhs.queue.length);
            this.ensureCapacity(maxSize + 1);
        }

        BinomialNode<T> carry = null;

        //i is the array index, j is the number of bits
        for (int i=0, j=1; j<=this.size; i++, j*=2 ) {

            BinomialNode<T> t1 = this.queue[i];
            BinomialNode<T> t2 = i<rhs.queue.length ? rhs.queue[i] : null;

            int whichCase = t1 == null ? 0 : 1;
            whichCase += t2 == null ? 0 : 2;
            whichCase += carry == null ? 0 : 4;

            switch (whichCase) {
                case 0: //no trees
                case 1: //only this
                    break;

                case 2: //only rhs
                    this.queue[i] = t2;
                    rhs.queue[i] = null;
                    break;

                case 3: //this and rhs
                    carry = this.combineTrees(t1, t2);
                    this.queue[i] = rhs.queue[i] = null;
                    break;

                case 4: //only carry
                    this.queue[i] = carry;
                    carry = null;
                    break;

                case 5: //this and carry
                    carry = this.combineTrees(t1, carry);
                    this.queue[i] = null;
                    break;

                case 6: //rhs and carry
                    carry = this.combineTrees(t2, carry);
                    rhs.queue[i] = null;
                    break;

                case 7: //all trees
                    this.queue[i] = carry;
                    carry = this.combineTrees(t1, t2);
                    rhs.queue[i] = null;
                    break;
            }

            for (int k=0; k<rhs.queue.length; k++)
                rhs.queue[k] = null;
            rhs.size = 0;
        }

    }

    // ensure binomial node array capacity
    private void ensureCapacity(int capacity) {
        BinomialNode<T>[] newTree = new BinomialNode[capacity];
        for (int i=0; i<this.queue.length; i++)
            newTree[i] = newTree[i];

        this.queue = newTree;
    }

    //merge two BinomialNode same size
    private BinomialNode combineTrees(BinomialNode b1, BinomialNode b2) {
        if (b1.element.compareTo(b2.element) > 0)
            return b2.addSubBinomialNode(b1);

        return b1.addSubBinomialNode(b2);
    }

    //get this iterator
    private Iterator<BinomialNode> iterator() {
        return new BinomialArrayQueueIterator();
    }

    //queue iterator
    private class BinomialArrayQueueIterator implements Iterator<BinomialNode> {

        private int current;

        private BinomialArrayQueueIterator() {
            while ( current < BinomialArrayQueue.this.queue.length &&
                    BinomialArrayQueue.this.queue[current] == null)
                current ++;
        }

        @Override
        public boolean hasNext() {
            return current < BinomialArrayQueue.this.queue.length;
        }

        @Override
        public BinomialNode next() {

            BinomialNode node = BinomialArrayQueue.this.queue[current++];
            while ( current < BinomialArrayQueue.this.queue.length &&
                    BinomialArrayQueue.this.queue[current] == null)
                current ++;

            return node;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
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
        private int degree;

        // add a sub binomial node,
        private BinomialNode addSubBinomialNode(BinomialNode b) {
            b.nextSibling = this.leftNode;
            this.leftNode = b;
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

    public static void main(String[] args) {

        BinomialArrayQueue<Integer> queue = new BinomialArrayQueue<Integer>();
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
