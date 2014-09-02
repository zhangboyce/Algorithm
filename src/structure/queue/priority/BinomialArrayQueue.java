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

    //can hold degree is 0,1,2,3, so max size is 1111 = 2^3 + 2^1 + 2^1 + 2^0 = 15
    private static final int DEFAULT_CAPACITY = 4;

    //binomial node array, node degree is the index in array
    private BinomialNode<T>[] array;

    //queue elements size
    private int size;

    public BinomialArrayQueue() {
        this(DEFAULT_CAPACITY);
    }

    public BinomialArrayQueue(int capacity) {
        if (capacity < 0)
            throw new IllegalArgumentException();

        this.array = new BinomialNode[capacity];
    }

    @Override
    public void offer(T t) {
        BinomialNode<T> node = new BinomialNode<T>(t);
        this.size += (1 << node.degree);
        this.insert(node);
    }

    @Override
    public T poll() {

        //find the min BinomialNode from queue
        BinomialNode<T> min = this.findMin();

        //remove the min BinomialNode from queue
        this.size -= (1 << min.degree);
        this.array[min.degree] = null;

        //add the min's children node into a new queue
        BinomialArrayQueue<T> children = new BinomialArrayQueue<T>(min.degree);
        BinomialNode<T> left = min.leftNode;
        while (left != null) {
            int degree = left.degree;
            children.array[degree] = left;
            children.size += (1 << degree);
            left = left.nextSibling;

            //remove the nextSibling of the root node
            children.array[degree].nextSibling = null;
        }

        //merge the new queue to this queue
        this.merge(children);

        return min.element;
    }

    @Override
    public T peek() {
        BinomialNode<T> min = this.findMin();
        return min.element;
    }

    // find the min node from queue
    private BinomialNode<T> findMin() {
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

        return min;
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
        this.array = new BinomialNode[DEFAULT_CAPACITY];
    }

    @Override
    public int indexOf(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(T t) {
        if (this.isEmpty()) return false;

        // iterate every node
        Iterator<BinomialNode> iterator = this.iterator();
        while (iterator.hasNext()) {
            if (this.contains(t, iterator.next()))
                return true;
        }
        return false;
    }

    //if current node element is equals t, return true.
    //else, find from current node's left or nextSibling recursive
    private boolean contains(T t, BinomialNode node) {
        if (node == null) return false;

        if (node.element.equals(t))
            return true;

        return this.contains(t, node.leftNode) || this.contains(t, node.nextSibling);
    }

    @Override
    public void addAll(IQueue<T> queue) {
        if (queue.isEmpty())
            throw new EmptyQueueException("Cannot access empty array.");

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

    //insert a binomial node to array
    private void insert(BinomialNode<T> node) {
        if (node == null) return;

        int i = node.degree;
        //if the degree > the max index, ensure capacity to degree + 1 (max index is degree)
        if (i > this.array.length-1) {
            this.ensureCapacity(i + 1);
            this.array[i] = node;
            return;

        }

        BinomialNode<T> b = this.array[i];
        if (b == null) {
            this.array[i] = node;
        } else {
            BinomialNode<T> carry = this.combineTrees(b, node);
            this.array[i] = null;
            this.insert(carry);
        }
    }

    //merge a arrayed binomial node array
    //like 101001110 + 110101 binary addition
    private void merge(BinomialArrayQueue<T> rhs) {
        if (this == rhs || null == rhs || rhs.isEmpty())
            return;

        this.size += rhs.size;
        int capacity = (int)(Math.log(this.size)/Math.log(2)) + 1;
        if (capacity > this.array.length) {
            int maxSize = Math.max(this.array.length, rhs.array.length) + 1;
            this.ensureCapacity(maxSize);
        }

        BinomialNode<T> carry = null;

        //i is the array index, j is the number of bits
        for (int i=0, j=1; j<=this.size; i++, j*=2 ) {

            BinomialNode<T> t1 = this.array[i];
            BinomialNode<T> t2 = i<rhs.array.length ? rhs.array[i] : null;

            int whichCase = t1 == null ? 0 : 1;
            whichCase += t2 == null ? 0 : 2;
            whichCase += carry == null ? 0 : 4;

            switch (whichCase) {
                case 0: //no trees
                case 1: //only this
                    break;

                case 2: //only rhs
                    this.array[i] = t2;
                    rhs.array[i] = null;
                    break;

                case 3: //this and rhs
                    carry = this.combineTrees(t1, t2);
                    this.array[i] = rhs.array[i] = null;
                    break;

                case 4: //only carry
                    this.array[i] = carry;
                    carry = null;
                    break;

                case 5: //this and carry
                    carry = this.combineTrees(t1, carry);
                    this.array[i] = null;
                    break;

                case 6: //rhs and carry
                    carry = this.combineTrees(t2, carry);
                    rhs.array[i] = null;
                    break;

                case 7: //all trees
                    this.array[i] = carry;
                    carry = this.combineTrees(t1, t2);
                    rhs.array[i] = null;
                    break;
            }

        }

        for (int k=0; k<rhs.array.length; k++)
            rhs.array[k] = null;
        rhs.size = 0;

    }

    // ensure binomial node array capacity
    private void ensureCapacity(int capacity) {
        BinomialNode<T>[] newTree = new BinomialNode[capacity];
        for (int i=0; i<this.array.length; i++)
            newTree[i] = this.array[i];

        this.array = newTree;
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

    //array iterator
    private class BinomialArrayQueueIterator implements Iterator<BinomialNode> {

        private int current;

        private BinomialArrayQueueIterator() {
            // init current index is the first not null index
            while ( current < BinomialArrayQueue.this.array.length &&
                    BinomialArrayQueue.this.array[current] == null)
                current ++;
        }

        @Override
        public boolean hasNext() {
            return current < BinomialArrayQueue.this.array.length;
        }

        @Override
        public BinomialNode next() {
            BinomialNode node = BinomialArrayQueue.this.array[current++];
            // set current index is the next not null index
            while ( current < BinomialArrayQueue.this.array.length &&
                    BinomialArrayQueue.this.array[current] == null)
                current ++;

            return node;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * binomial array node
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


        //array.display();

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
