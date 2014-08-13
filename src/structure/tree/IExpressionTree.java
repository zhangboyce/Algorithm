package structure.tree;

import common.utils.ObjectUtils;
import structure.application.PostfixExpression;
import structure.stack.ILinkedStack;
import structure.stack.IStack;

/**
 * Created by boyce on 2014/8/3.   
 */
public class IExpressionTree {

    public static void main(String[] args) {
        String expression = "2+(3+4)*5+1";
        String postfixExpression = PostfixExpression.toPostfixExpression(expression);
        System.out.println(postfixExpression);


        IExpressionTree iExpressionTree = new IExpressionTree(postfixExpression);
        System.out.println(iExpressionTree.getPrefixExpression());
        System.out.println(iExpressionTree.getInfixExpression());
        System.out.println(iExpressionTree.getPostfixExpression());
    }

    private IStack<Node> tree;
    private String postfixExpression;

    public IExpressionTree(String postfixExpression) {
        char[] chars = postfixExpression.toCharArray();
        tree = new ILinkedStack<Node>();
        this.postfixExpression = postfixExpression;

        for (char c: chars) {
            if (!isOperator(c))
                tree.push(new Node(c));
            else {
                // 先弹出的是右子树
                Node rightNode = tree.pop();
                Node leftNode = tree.pop();
                tree.push(new Node(c, leftNode, rightNode));
            }
        }
    }

    /**
     * get infix expression
     */
    public String getInfixExpression() {
        Node node = tree.peek();
        return getInfixExpression(node);
    }

    private String getInfixExpression(Node node) {

        if (ObjectUtils.isNull(node))
            return "";

        String result = "";

        //if the leftNode is not null, the rightNode is not null also
        if (ObjectUtils.isNotNull(node.leftNode))
            result += "(";

        result += getInfixExpression(node.leftNode);
        result += node.element.toString();
        result += getInfixExpression(node.rightNode);

        if (ObjectUtils.isNotNull(node.leftNode))
            result += ")";

        return result;
    }

    /**
     * get postfix expression
     */
    public String getPostfixExpression() {
        return this.postfixExpression;
    }

    /**
     * get prefix expression
     */
    public String getPrefixExpression() {
        Node node = tree.peek();
        return getPrefixExpression(node);
    }

    private String getPrefixExpression(Node node) {

        if (ObjectUtils.isNull(node))
            return "";

        String result = "";
        result += node.element.toString();
        result += getPrefixExpression(node.leftNode);
        result += getPrefixExpression(node.rightNode);

        return result;
    }

    private String toString(Node node) {
        return node.element.toString();
    }

    private static boolean isOperator(char c) {
        return  c == '+' ||
                c == '-' ||
                c == '*' ||
                c == '/';
    }

    private static class Node<T extends Comparable> {
        T element;
        Node leftNode;
        Node rightNode;

        public Node(T element, Node leftNode, Node rightNode) {
            this.element = element;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
        }

        public Node(T element) {
            this(element, null, null);
        }

    }
}
