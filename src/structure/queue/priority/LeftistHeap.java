package structure.queue.priority;

import common.exception.EmptyQueueException;
import structure.queue.ILinkedQueue;
import structure.queue.IQueue;
import structure.tree.ITree;
import structure.tree.TreePrinter;

/**
 * Created by boyce on 2014/8/27.
 */
public class LeftistHeap<T extends Comparable> implements IPriorityQueue<T> {

    private LeftistNode root;
    private int size;

    public LeftistHeap() {
    }

    public LeftistHeap(LeftistHeap<T> heap) {
        if (heap != this && null != heap) {
            this.root = this.merge(this.root, heap.root);
        }
    }

    @Override
    public void offer(T t) {
        LeftistNode node = new LeftistNode(t);
        this.root = this.merge(root, node);

        this.size ++;
    }

    @Override
    public T poll() {
        if (this.isEmpty())
            throw new EmptyQueueException();

        T element = this.peek();
        this.root = this.merge(this.root.rightNode, this.root.leftNode);
        return element;
    }

    @Override
    public T peek() {
        if (this.isEmpty())
            throw new EmptyQueueException();

        return (T)this.root.element;
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
        if (this.isEmpty() || null == t)
            return -1;

        IQueue<LeftistNode> queue = new ILinkedQueue<LeftistNode>();
        queue.offer(root);
        return this.indexOf(queue, t, -1);
    }

    private int indexOf(IQueue<LeftistNode> queue, T t, int index) {
        if(queue.isEmpty())
            return index;

        LeftistNode node = queue.poll();
        if (null != node && node.element.equals(t)) {
           return index;

        } else {
            queue.offer(node.leftNode);
            queue.offer(node.rightNode);
        }
        return this.indexOf(queue, t, index ++);

    }

    @Override
    public boolean contains(T t) {
        return -1 != this.indexOf(t);
    }

    @Override
    public void addAll(IQueue<T> queue) {
        if (queue.isEmpty()) return;

        while (!queue.isEmpty())
            this.offer(queue.poll());
    }

    @Override
    public void display() {
        TreePrinter.printNode(root);
    }

    //merge node1, node2
    private LeftistNode merge(LeftistNode node1, LeftistNode node2) {
        if (null == node1)
            return node2;

        if (null == node2)
            return node1;

        if (node1.element.compareTo(node2.element) > 0)
           return this.merge1(node2, node1);

        return this.merge1(node1, node2);
    }

    //merge node1, node2 and node1.element < node2.element
    private LeftistNode merge1(LeftistNode node1, LeftistNode node2) {

        //
        if (node1.leftNode == null)
            node1.leftNode = node2;

        else {
            node1.rightNode = this.merge(node1.rightNode, node2);

            //swap child, keep left node npl >= right node npl
            if (this.npl(node1.leftNode) < this.npl(node1.rightNode)) {
                LeftistNode temp = node1.leftNode;
                node1.leftNode = node1.rightNode;
                node1.rightNode = temp;
            }
            // npl = rightChild npl(the less one) + 1
            node1.npl = node1.rightNode.npl + 1;
        }
        return node1;
    }

    private int npl(LeftistNode node) {
        if (null == node)
            return -1;

        return node.npl;
    }

    private static class LeftistNode<T extends Comparable> implements ITree.INode<T> {

        private T element;
        private LeftistNode leftNode;
        private LeftistNode rightNode;
        private int npl;

        private LeftistNode(T element) {
            this.element = element;
        }

        @Override
        public T getElement() {
            return element;
        }

        @Override
        public LeftistNode getRightNode() {
            return rightNode;
        }

        @Override
        public LeftistNode getLeftNode() {
            return leftNode;
        }
    }

    public static void main(String[] args) {
        LeftistHeap heap = new LeftistHeap();
        heap.offer(1);
        heap.offer(4);
        heap.offer(12);
        heap.offer(7);
        heap.offer(5);
        heap.offer(16);
        heap.offer(3);
        heap.offer(8);

        heap.display();
        System.out.println(heap.peek());
        System.out.println(heap.poll());

        heap.display();
    }
}
