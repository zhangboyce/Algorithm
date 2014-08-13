package structure.tree;

import structure.queue.ILinkedQueue;
import structure.queue.IQueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by boyce on 2014/8/5.
 */
public abstract class IAbstractTree<T extends Comparable> implements ITree<T> {

    protected Node root;

    @Override
    public int deep() {
        return this.deep(root);
    }

    //Gets the node height
    protected int height(Node node) {
        return null == node ? -1 : node.height;
    }

    /**
     * Gets a specified node depth
     * @param node the specified node
     * @return the node depth
     */
    protected abstract int deep(Node node);

    @Override
    public T findMax() {
        return this.findMax(root);
    }

    /**
     * Gets the maximum element under the specified node
     * @param node the specified node
     * @return the maximum element
     */
    protected abstract T findMax(Node node);

    @Override
    public T findMin() {
        return this.findMin(root);
    }

    /**
     * Gets the minimum element under the specified node
     * @param node the specified node
     * @return the minimum element
     */
    protected abstract T findMin(Node node);

    @Override
    public boolean contains(T t) {
        return this.contains(t, root);
    }

    /**
     * Whether the specified node contains the element T
     * @param t
     * @param node the specified node
     * @return
     */
    protected abstract boolean contains(T t, Node node);

    @Override
    public boolean isEmpty() {
        return null  == root;
    }

    @Override
    public void insert(T element) {
        this.root = this.insert(element, root);
    }

    /**
     * Inserts the element t into the specified node
     * @param element
     * @param node
     * @return
     */
    protected abstract Node insert(T element, Node node);

    @Override
    public void remove(T element) {
        this.remove(element, root);
    }

    /**
     * Removes element under the specified node
     * @param element
     * @param node
     */
    protected abstract Node remove(T element, Node node);

    @Override
    public String LDR() {
        StringBuilder builder = new StringBuilder("[");
        this.toStringLDR(root, builder);

        if (builder.length() > 3)
            builder.delete(builder.length() - 2, builder.length());

        return builder.append("]").toString();
    }

    private void toStringLDR(Node node, StringBuilder builder) {
        if (null == node)
            return;

        this.toStringLDR(node.leftNode, builder);
        builder.append(node.element.toString()).append(", ");
        this.toStringLDR(node.rightNode, builder);
    }

    @Override
    public String LRD() {
        StringBuilder builder = new StringBuilder("[");
        this.toStringLRD(root, builder);

        if (builder.length() > 3)
            builder.delete(builder.length() - 2, builder.length());

        return builder.append("]").toString();
    }

    private void toStringLRD(Node node, StringBuilder builder) {
        if (null == node)
            return;

        this.toStringLRD(node.leftNode,  builder);
        this.toStringLRD(node.rightNode, builder);
        builder.append(node.element.toString()).append(", ");
    }

    @Override
    public String DLR(){
        StringBuilder builder = new StringBuilder("[");
        this.toStringDLR(root, builder);

        if (builder.length() > 3)
            builder.delete(builder.length() - 2, builder.length());

        return builder.append("]").toString();
    }

    private void toStringDLR(Node node, StringBuilder builder) {
        if (null == node)
            return;

        builder.append(node.element.toString()).append(", ");
        this.toStringDLR(node.leftNode, builder);
        this.toStringDLR(node.rightNode, builder);
    }

    @Override
    public String toString() {
        IQueue<Node> queue = new ILinkedQueue<Node>();
        queue.offer(root);

        StringBuilder builder = new StringBuilder("[");
        this.toString(queue, builder);

        if (builder.length() > 3)
            builder.delete(builder.length() - 2, builder.length());

        return builder.append("]").toString();
    }

    /**
     * 层序遍历
     */
    private void toString(IQueue<Node> queue, StringBuilder builder) {
        if(queue.isEmpty())
            return;

        Node node = queue.poll();
        if (null != node) {
            builder.append(node.element.toString()).append(", ");
            queue.offer(node.leftNode);
            queue.offer(node.rightNode);
        }

        this.toString(queue, builder);
    }

    @Override
    public void display() {
        BTreePrinter.printNode(root);
    }

    public static class BTreePrinter {
        public static <T extends Comparable<?>> void printNode(Node<T> root) {
            int maxLevel = BTreePrinter.maxLevel(root);

            printNodeInternal(Collections.singletonList(root), 1, maxLevel);
        }

        private static <T extends Comparable<?>> void printNodeInternal(List<Node<T>> nodes, int level, int maxLevel) {
            if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes))
                return;

            int floor = maxLevel - level;
            int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
            int firstSpaces = (int) Math.pow(2, (floor)) - 1;
            int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

            BTreePrinter.printWhitespaces(firstSpaces);

            List<Node<T>> newNodes = new ArrayList<Node<T>>();
            for (Node<T> node : nodes) {
                if (node != null) {
                    System.out.print(node.element);
                    newNodes.add(node.leftNode);
                    newNodes.add(node.rightNode);
                } else {
                    newNodes.add(null);
                    newNodes.add(null);
                    System.out.print(" ");
                }

                BTreePrinter.printWhitespaces(betweenSpaces);
            }
            System.out.println("");

            for (int i = 1; i <= endgeLines; i++) {
                for (int j = 0; j < nodes.size(); j++) {
                    BTreePrinter.printWhitespaces(firstSpaces - i);
                    if (nodes.get(j) == null) {
                        BTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                        continue;
                    }

                    if (nodes.get(j).leftNode != null)
                        System.out.print("/");
                    else
                        BTreePrinter.printWhitespaces(1);

                    BTreePrinter.printWhitespaces(i + i - 1);

                    if (nodes.get(j).rightNode != null)
                        System.out.print("\\");
                    else
                        BTreePrinter.printWhitespaces(1);

                    BTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
                }

                System.out.println("");
            }

            printNodeInternal(newNodes, level + 1, maxLevel);
        }

        private static void printWhitespaces(int count) {
            for (int i = 0; i < count; i++)
                System.out.print(" ");
        }

        private static <T extends Comparable<?>> int maxLevel(Node<T> node) {
            if (node == null)
                return 0;

            return Math.max(BTreePrinter.maxLevel(node.leftNode), BTreePrinter.maxLevel(node.rightNode)) + 1;
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
