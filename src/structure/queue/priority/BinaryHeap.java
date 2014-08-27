package structure.queue.priority;

import structure.queue.IQueue;
import structure.tree.ITree;
import structure.tree.TreePrinter;

/**
 * Created by boyce on 2014/8/27.
 */
public class BinaryHeap<T extends Comparable> extends DForkHeap<T> {

    protected BinaryHeap() {
    }

    protected BinaryHeap(int capacity) {
        super(capacity);
    }

    protected BinaryHeap(T[] elements) {
        super(elements);
    }

    //build heap
    protected void buildHeap() {
        if (this.isEmpty())
            return;

        for (int i=currentSize/2; i>=0; i--)
            this.trickleDown(i);
    }

    //trickle up the element indexOf i
    protected void trickleUp(int i) {

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

    //trickle down the element indexOf i
    protected void trickleDown(int i) {

        int left = this.leftChild(i);
        int right = this.rightChild(i);
        T element = this.elements[i];

        if (left < currentSize && right < currentSize) {
            int min = this.minPosition(right, left);

            if ( element.compareTo(this.elements[min]) > 0)
                this.swapTrickleDown(i, min);

        } else if (left < currentSize && element.compareTo(this.elements[left]) > 0)
            this.swapTrickleDown(i, left);

    }

    private void swapTrickleDown(int i, int child) {
        T temp = this.elements[i];
        this.elements[i] = this.elements[child];
        this.elements[child] = temp;

        this.trickleDown(child);
    }

    private int minPosition(int i1, int i2) {
        T t1 = this.elements[i1];
        T t2 = this.elements[i2];

        return t1.compareTo(t2)<0 ? i1 : i2;
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

    //if current node indexOf is i, the left child indexOf is 2*i + 1
    protected int leftChild(int i) {
        return (i << 1) + 1;
    }

    //if current node indexOf is i, the right child indexOf is 2*i + 2
    protected int rightChild(int i) {
        return (i + 1) << 1;
    }

    //if current node indexOf is i, the parent node indexOf is (i-1)/2
    protected int parent(int i) {
        return (i - 1) >> 1;
    }

    public static void main(String[] args) {

        Integer[] elements = {3, 4, 5, 1, 8, 9};
        IPriorityQueue iPriorityQueue = new BinaryHeap(elements);
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
