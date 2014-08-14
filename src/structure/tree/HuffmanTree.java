package structure.tree;

import structure.heap.IArrayPriorityQueue;
import structure.heap.IPriorityQueue;

/**
 * Created by boyce on 2014/8/14.
 */
public class HuffmanTree<T extends Comparable> {

    private HuffmanNode root;

    public HuffmanTree(T[] elements) {
        IPriorityQueue<HuffmanNode> iPriorityQueue = new IArrayPriorityQueue<HuffmanNode>();

        for (T element: elements) {
            HuffmanNode huffmanNode = new HuffmanNode(element);
            iPriorityQueue.offer(huffmanNode);
        }

        while (iPriorityQueue.size() > 1) {
            HuffmanNode left = iPriorityQueue.poll();
            HuffmanNode right = iPriorityQueue.poll();

            HuffmanNode parent = new HuffmanNode(left.wight + right.wight, left, right);
            iPriorityQueue.offer(parent);
        }

        root = iPriorityQueue.poll();
        iPriorityQueue.clear();
    }

    public void display() {
        TreePrinter.printNode(root);
    }

    /**
     * huffman tree node
     * @param <T>
     */
    static class HuffmanNode<T extends Comparable>
            implements Comparable<HuffmanNode>, ITree.INode<T> {

        protected T element;
        protected HuffmanNode leftNode;
        protected HuffmanNode rightNode;
        protected int wight;

        public HuffmanNode(T element, int wight, HuffmanNode leftNode, HuffmanNode rightNode) {
            this.element = element;
            this.wight = wight;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
        }

        public HuffmanNode(int wight, HuffmanNode leftNode, HuffmanNode rightNode) {
            this(null, wight, leftNode, rightNode);
        }

        public HuffmanNode(T element, HuffmanNode leftNode, HuffmanNode rightNode) {

            try {
                wight = Integer.valueOf(String.valueOf(element));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            this.element = element;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
        }

        public HuffmanNode(T element, int wight) {
            this(element, wight, null, null);
        }

        public HuffmanNode(T element) {
            try {
                wight = Integer.valueOf(String.valueOf(element));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            this.element = element;
        }

        @Override
        public int compareTo(HuffmanNode o) {
            return this.wight - o.wight;
        }

        public T getElement() {
            return element;
        }

        public HuffmanNode getLeftNode() {
            return leftNode;
        }

        public HuffmanNode getRightNode() {
            return rightNode;
        }
    }
}
