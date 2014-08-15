package structure.heap;

import structure.tree.ITree;
import structure.tree.TreePrinter;

/**
 * Created by boyce on 2014/8/14.
 */
public class MinHeap<T extends Comparable> implements IPriorityQueue<T> {

    private final static int DEFAULT_CAPACITY = 10;

    private int currentSize;
    private T[] elements;

    public MinHeap() {
        this(DEFAULT_CAPACITY);
    }

    public MinHeap(int capacity) {
        this.elements = (T[])new Comparable[capacity];
    }

    public MinHeap(T[] elements) {
        this.elements = elements;
        this.currentSize = this.elements.length;

        this.buildHeap();
    }

    @Override
    public void offer(T t) {
        if (currentSize == this.elements.length)
            this.enlargeCapacity(this.elements.length * 2);

        this.elements[currentSize] = t;
        this.trickleUp(currentSize ++);
    }

    @Override
    public T poll() {
        T element = this.peek();

        //remove root element, and set the last element as root
        this.elements[0] = this.elements[currentSize-1];

        //trickle down the root element to correct position
        this.trickleDown(0);
        currentSize --;

        return element;
    }

    @Override
    public T peek() {
        return this.elements[0];
    }

    @Override
    public int size() {
        return this.currentSize;
    }

    @Override
    public boolean isEmpty() {
        return currentSize == 0;
    }

    @Override
    public void clear() {
        this.elements = (T[])new Comparable[DEFAULT_CAPACITY];
        this.currentSize = 0;
    }

    //trickle up the element index i
    private void trickleUp(int i) {

        // root cannot trickleUp
        if (i > 0) {
            T element = this.elements[i];
            int parent = this.parent(i);
            T parentElement = this.elements[parent];

            //if the element is less than it's parent, swap
            if (element.compareTo(parentElement) < 0) {
                this.elements[i] = parentElement;
                this.elements[parent] = element;

                trickleUp(parent);
            }
        }
    }

    //trickle down the element index i
    private void trickleDown(int i) {

        int left = this.leftChild(i);
        int right = this.rightChild(i);

        T element = this.elements[i];

        //left < current, at least exist left leftChild
        if (left < currentSize) {
            //right < current, rightChild exist, leftChild exist also (left+1 = right)
            if (right < currentSize && this.elements[right].compareTo(this.elements[left]) < 0
                    && element.compareTo(this.elements[right]) > 0) {

                T temp = this.elements[i];
                this.elements[i] = this.elements[right];
                this.elements[right] = temp;

                this.trickleDown(right);
            }
            else if (element.compareTo(this.elements[left]) > 0) {

                T temp = this.elements[i];
                this.elements[i] = this.elements[left];
                this.elements[left] = temp;

                this.trickleDown(left);
            }
        }

    }

    //build heap
    private void buildHeap() {
       if (this.isEmpty())
           return;

        for (int i=currentSize-1; i>=0; i--)
            this.trickleDown(i);
    }

    //if current node index is i, the left child index is 2*i + 1
    private int leftChild(int i) {
        return (i << 1) + 1;
    }

    //if current node index is i, the right child index is 2*i + 2
    private int rightChild(int i) {
        return (i + 1) << 1;
    }

    //if current node index is i, the parent node index is (i-1)/2
    private int parent(int i) {
        return (i - 1) >> 1;
    }

    //enlarge the capacity
    private void enlargeCapacity(int capacity) {
        T[] newElements = (T[])new Comparable[capacity];
        for (int i=0; i<this.elements.length; i++)
            newElements[i] = this.elements[i];

        this.elements = newElements;
    }

    //display min head as a tree
    public void display() {
        ITree.Node root = this.createNode(0);
        TreePrinter.printNode(root);
    }

    //convert the array elements to tree
    private ITree.Node createNode(int i) {
        if (i < currentSize) {
            ITree.Node node = new ITree.Node(this.elements[i]);
            node.setLeftNode(createNode(this.leftChild(i)));
            node.setRightNode(createNode(this.rightChild(i)));

            return node;
        }
        return null;
    }

    public static void main(String[] args) {

        Integer[] elements = {3, 4, 5, 1, 8, 9};
        IPriorityQueue iPriorityQueue = new MinHeap(elements);
        iPriorityQueue.display();

        iPriorityQueue.offer(6);
        iPriorityQueue.offer(0);
        iPriorityQueue.offer(7);
        iPriorityQueue.offer(2);

        iPriorityQueue.display();
        iPriorityQueue.poll();
        iPriorityQueue.display();
    }
}
