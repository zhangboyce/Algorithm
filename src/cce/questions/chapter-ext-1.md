## 扩展 1：链表与树的复习与扩展
#### 一、填空题（共16空，每空4分。共64分）

1. 开发微信图文编辑器中的撤销功能应该使用\____数据结构。

2. 开发聊天室中聊天内容的处理与中转应该使用\____数据结构。

3. 开发在线娃娃机的排队等待功能应该使用\____数据结构。

4. 针对用户数据的年龄进行索引，以便提高检索效率应该使用\____数据结构。

5. 开发一个简易的JSON解析器，在解析过程中应该使用\____数据结构。

6. 假设在64位寻址空间的计算机内存中，有一个长度为n的链表，链表中存放了4字节的整型数据，此链表占用的内存空间为\_\_\_\_。如果链表位双向循环链表，则此链表占用的内存空间为\_\_\_\_。如果是有n个节点的二叉树，则树占用的内存空间为\____。

7. 在二叉树中，每个节点分别有一个左子树与右子树的引用变量，在一个n个节点的二叉树中，共有\_\_\_\_个空引用。假设在32位的寻址空间的计算机内存中，共浪费\____内存。

8. BinarySearchTree如果需要数据由小到大进行遍历应该使用\_\_\_\_的遍历方式。

9. 如果需要查找BinarySearchTree中根节点的前驱节点，最坏的情况下时间复杂度是\_\_\_\_。

10. 针对BinarySearchTree，在不使用递归的情况下完成节点的查找和插入，请补充未完成的代码：

    ```java
    class Node {
        int data; // the data in the node
        Node left; 
        Node right;
    }
    
    class BinarySearchTree {
        
        private Node root;
        
    	public boolean contains(Node node) {
            Node x = root;
            while (x != null) {
                _____________________________________;
                _____________________________________;
                _____________________________________;
            }
            return false;
        }
    
        public void put(Node node) {
            if (root == null) {
                root = node;
                return;
            }
    
            Node parent = null, x = root;
            while (x != null) {
                parent = x;
                if (node.data < x.data) x = x.left;
                else  x = x.right;
                else {
                    return; 
                }
            }
    
            _____________________________________;
            _____________________________________;
        }
    }
    ```

    

#### 二、代码阅读题（共3题，每4题分。共12分）

在Node作为BinarySearchTree的节点的前提下，下面的函数分别起到什么作用。

```java
/*
 * __________________________________
 */
private int func1(Node x) {
    if (x == null) return -1;
    return 1 + Math.max(func1(x.left), func1(x.right));
}

/*
 * __________________________________
 */
public Node func2(Node node) {
    Node x = func2(root, node);
    if (x == null) return null;
    else return x;
}

private Node func2(Node x, Node node) {
    if (x == null) return null;
    if (node.data == x.data) return x;
    if (node.data < x.data) { 
        Node t = func2(x.left, node); 
        if (t != null) return t;
        else return x; 
    } 
    return func2(x.right, node); 
} 

/*
 * __________________________________
 */
public Node func3(Node node) {
    return func3(root, node, null);
}

private Node func3(Node x, Node node, Node best) {
    if (x == null) return best;
    if (node.data < x.data) return func3(x.left, node, best);
    else if (node.data > x.data) return func3(x.right, node, x);
    else return x;
} 
```



####三、解答题（共4题，每题6分。共24分）

1. 如果链表中最后一个节点的next指向了链表中的其他节点，则该链表中存在环路。请实现一种检查链表是否存在环路的方法。











2. 为BinarySearchTree实现一个方法 query(int start, int end)，查询出树中在start与end区间内的全部数据。









3. 已知BinarySearchTree中的任一节点node，请尝试设计一种查找该节点的前驱与后继节点的方案。









4. BinarySearchTree中的节点在查找前驱与后继节点的过程是否有改进的空间？请提供一个大致的思路。