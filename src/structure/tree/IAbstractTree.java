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
        TreePrinter.printNode(root);
    }

}
