package structure.tree;

import structure.queue.ILinkedQueue;
import structure.queue.IQueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by boyce on 2014/7/22.
 */
public class IBinaryTree<T extends Comparable> extends IAbstractTree<T>{

    /**
     * 叶节点的高度为0，非叶节点的高度等于左子树和右子树中高度较大者 + 1
     * @param node the specified node
     * @return
     */
    protected int deep(Node node) {
        if (null == node)
            return 0;

        int left = this.deep(node.leftNode);
        int right = this.deep(node.rightNode);

        return left > right ? left +1 : right + 1;
    }

    /**
     * 一直沿着节点往右查找，找到最右的叶节点就是最大节点
     * @param node the specified node
     * @return
     */
    protected T findMax(Node node) {
        if (isEmpty())
            return null;

        if (null != node.rightNode)
            return this.findMax(node.rightNode);
        else
            return (T)node.element;
    }

    /**
     * 一直沿着节点往左寻找，找到最左的叶节点就是最小节点
     * @param node the specified node
     * @return
     */
    protected T findMin(Node node) {
        if (isEmpty())
            return null;

        if (null != node.leftNode)
            return this.findMin(node.leftNode);
        else
            return (T)node.element;
    }

    /**
     * 比较查找元素element和当前节点大小，确定往左还是往右查找。查找到相等的节点，返回true
     * @param element
     * @param node the specified node
     * @return
     */
    protected boolean contains(T element, Node node) {
        if (null == node)
            return false;

        int compareResult = element.compareTo(node.element);
        if (compareResult < 0)
            return contains(element, node.leftNode);
        else if (compareResult > 0)
            return contains(element, node.rightNode);
        else
            return true;
    }

    /**
     * 比较要添加的元素element和当前节点大小，确定往左还是往右添加，直到找到null，将当前element添加成叶节点
     * @param element
     * @param node
     * @return
     */
    protected Node insert(T element, Node node) {
        if (null == node)
            return new Node(element);

        int compareResult = element.compareTo(node.element);
        if (compareResult < 0)
            node.leftNode = insert(element, node.leftNode);
        else if (compareResult > 0)
            node.rightNode = insert(element, node.rightNode);

        return node;
    }

    /**
     * 比较要删除的元素element和当前节点大小，确定往左还是往右查找，直到找到与element相等的Node。
     * 如果该Node是叶节点，直接删除（Node = null）。
     * 如果该节点有一个右节点或者一个左节点，将用其左节点或者右节点替换该节点（Node = Node.rightNode, Node = Node.leftNode）。
     * 如果该节点是一个满节点，将该节点的元素替换成左子树中最大的元素或者右子树中最小的元素，然后再递归删除左子树中最大的元素或者右子树中最小的元素。
     * 综上所述：实际上最终真正移除的Node都是叶节点。
     *
     * @param element
     * @param node
     * @return
     */
    protected Node remove(T element, Node node) {
        if (null == node)
            return node;

        int compareResult = element.compareTo(node.element);
        if (compareResult < 0)
            node.leftNode = remove(element, node.leftNode);
        else if (compareResult > 0)
            node.rightNode = remove(element, node.rightNode);

        //remove
        else if (node.isFullLeaf()){
            T leftMax = this.findMax(node.leftNode);
            node.element = leftMax;
            node.rightNode = remove(leftMax, node.rightNode);
        }
        else
            node = null != node.rightNode ? node.rightNode : node.leftNode;

        return node;
    }
}
