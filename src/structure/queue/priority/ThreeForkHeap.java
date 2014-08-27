package structure.queue.priority;

import structure.queue.IQueue;
import structure.tree.ITree;
import structure.tree.TreePrinter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by boyce on 2014/8/27.
 */
public class ThreeForkHeap<T extends Comparable> extends DForkHeap<T> {

    public ThreeForkHeap() {
    }

    public ThreeForkHeap(int capacity) {
        super(capacity);
    }

    public ThreeForkHeap(T[] elements) {
        super(elements);
    }

    /**
     * trickleUp method
     * @param i
     */
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

    /**
     * trickleDown method
     * @param i
     */
    protected void trickleDown(int i) {
        int left = this.leftChild(i);
        int mid = this.midChild(i);
        int right = this.rightChild(i);

        T element = this.elements[i];

        //left, mid, right exist
        if (left < currentSize && mid < currentSize && right < currentSize) {
            int min = this.minPosition(left, mid, right);

            if (element.compareTo(this.elements[min]) > 0)
                this.swapTrickleDown(i, min);

        //left, mid exist
        } else if (left < currentSize && mid < currentSize ) {
            int min = this.minPosition(left, mid);
            if (element.compareTo(this.elements[min]) > 0)
                this.swapTrickleDown(i, min);

        // only left exist
        } else if (left < currentSize && element.compareTo(this.elements[left]) > 0)
            this.swapTrickleDown(i, left);
    }

    private void swapTrickleDown(int i, int child) {
        T temp = this.elements[i];
        this.elements[i] = this.elements[child];
        this.elements[child] = temp;

        this.trickleDown(child);
    }

    private int minPosition(int i1, int i2, int i3) {
        int min1 = this.minPosition(i1, i2);
        int min = this.minPosition(min1, i3);

        return min;
    }

    private int minPosition(int i1, int i2) {
        T t1 = this.elements[i1];
        T t2 = this.elements[i2];

        return t1.compareTo(t2)<0 ? i1 : i2;
    }

    //build heap
    protected void buildHeap() {
        if (this.isEmpty())
            return;

        for (int i=currentSize/3; i>=0; i--)
            this.trickleDown(i);
    }

    //display min head as a tree
    public void display() {
        ThreeForkNode root = this.createNode(0);
        TreePrinter.printNode(root);
    }

    //if current node indexOf is i, the left child indexOf is 3*i + 1
    protected int leftChild(int i) {
        return (i * 3) + 1;
    }

    //if current node indexOf is i, the right child indexOf is 3*i + 3
    protected int rightChild(int i) {
        return (i + 1) * 3;
    }

    //if current node indexOf is i, the right child indexOf is 3*i + 2
    protected int midChild(int i) {
        return (i * 3) + 2;
    }

    //if current node indexOf is i, the parent node indexOf is (i-1)/3
    protected int parent(int i) {
        return (i - 1)/3;
    }

    public static void main(String[] args) {

        Integer[] elements = {3, 4, 5, 1, 8, 9};
        IPriorityQueue iPriorityQueue = new ThreeForkHeap(elements);
        iPriorityQueue.display();

        iPriorityQueue.offer(6);
        iPriorityQueue.offer(0);
        iPriorityQueue.offer(7);
        iPriorityQueue.offer(2);

        //iPriorityQueue.display();
        iPriorityQueue.poll();
        //iPriorityQueue.display();
    }

    private static class ThreeForkNode<T> {
        private T element;
        private ThreeForkNode leftNode;
        private ThreeForkNode midNode;
        private ThreeForkNode rightNode;

        private ThreeForkNode(T element) {
            this.element = element;
        }
    }

    //convert the array elements to tree
    private ThreeForkNode createNode(int i) {
        if (i < currentSize) {
            ThreeForkNode node = new ThreeForkNode(this.elements[i]);
            node.leftNode = createNode(this.leftChild(i));
            node.midNode = createNode(this.midChild(i));
            node.rightNode = createNode(this.rightChild(i));

            return node;
        }
        return null;
    }

    private static class TreePrinter {
        public static <T extends Comparable<?>> void printNode(ThreeForkNode<T> root) {

            int maxLevel = TreePrinter.maxLevel(root);
            printNodeInternal(Collections.singletonList(root), 1, maxLevel);
        }

        private static <T extends Comparable<?>> void printNodeInternal(List<ThreeForkNode<T>> nodes, int level, int maxLevel) {
            if (nodes.isEmpty() || TreePrinter.isAllElementsNull(nodes))
                return;

            int floor = maxLevel - level;
            int endgeLines = (int) Math.pow(3, (Math.max(floor - 1, 0)));
            int firstSpaces = (int) Math.pow(3, (floor)) - 1;
            int betweenSpaces = (int) Math.pow(3, (floor + 1)) - 1;

            TreePrinter.printWhitespaces(firstSpaces);

            List<ThreeForkNode<T>> newNodes = new ArrayList<ThreeForkNode<T>>();
            for (ThreeForkNode<T> node : nodes) {
                if (node != null) {
                    System.out.print(node.element);
                    newNodes.add(node.leftNode);
                    newNodes.add(node.midNode);
                    newNodes.add(node.rightNode);
                } else {
                    newNodes.add(null);
                    newNodes.add(null);
                    newNodes.add(null);
                    System.out.print(" ");
                }

                TreePrinter.printWhitespaces(betweenSpaces);
            }
            System.out.println("");

            for (int i = 1; i <= endgeLines; i++) {
                for (int j = 0; j < nodes.size(); j++) {
                    TreePrinter.printWhitespaces(firstSpaces - i);
                    if (nodes.get(j) == null) {
                        TreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                        continue;
                    }

                    if (nodes.get(j).leftNode != null)
                        System.out.print("/");
                    else
                        TreePrinter.printWhitespaces(1);
                    TreePrinter.printWhitespaces(2*i - 1);

                    if (nodes.get(j).midNode != null)
                        System.out.print("|");
                    else
                        TreePrinter.printWhitespaces(1);
                    TreePrinter.printWhitespaces(2*i - 1);

                    if (nodes.get(j).rightNode != null)
                        System.out.print("\\");
                    else
                        TreePrinter.printWhitespaces(1);
                    TreePrinter.printWhitespaces(endgeLines + endgeLines - i);
                }

                System.out.println("");
            }

            printNodeInternal(newNodes, level + 1, maxLevel);
        }

        private static void printWhitespaces(int count) {
            for (int i = 0; i < count; i++)
                System.out.print(" ");
        }

        private static <T extends Comparable<?>> int maxLevel(ThreeForkNode node) {
            if (node == null)
                return 0;

            int max1 = Math.max(TreePrinter.maxLevel(node.leftNode), TreePrinter.maxLevel(node.rightNode));
            int max = Math.max(max1, TreePrinter.maxLevel(node.midNode));

            return max + 1;
        }

        private static <T> boolean isAllElementsNull(List<T> list) {
            for (Object object : list) {
                if (object != null)
                    return false;
            }

            return true;
        }
    }
}
