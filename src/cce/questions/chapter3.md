## 第三章：树的基本操作
#### 一、填空题（共10题，每题5分。共50分）

1. 我们定义一个二叉查找树的节点类Node以及二叉查找树类BinarySearchTree，请补充未完成的代码:

```java
class Node {
    int data; // the data in the node
    Node left; // left child
    Node right; // right child
}
class BinarySearchTree {
    private Node root;
    public boolean isEmpty() {
         ________________________;
    }
    public boolean contains(Node x) {
        return this.contains(x, root);
    }
    private boolean contains(Node x, Node n) {
        if (n == null) return false;
        if (x.data < n.data) ________________________;
        else if (x.data > n.data) ________________________;
        else ________________________;
    }
    
    public int findMin() {
        this.findMin(root).data;
    }
    private Node findMin(Node n) {
        if(t == null) return null;
        else if (t.left == null) ________________________;
        else ________________________;
    }
    
    public void insert(Node n) {
        root = this.insert(n, root);
    }
    private Node insert(Node n, Node t) {
        if (t == null) return n;
        if (n.data < t.data) ________________________;
        else if (n.data > t.data) ________________________;
        return t;
    }
}
```

2. 回顾我们第二章的解答题最后一题，我们如果不断地对一棵理想的二叉查找树进行删除或插入操作，有可能破坏这棵树的平衡即树的高度不再是$O(\log{n})​$。那么为了保证树的平衡，我们可以设立一些条件，比如要求每一个节点都必须有相同高度的左子树和右子树，但是这样要求又太严格，因为如果树只有4个节点将永远无法满足平衡，高度为$h​$的树必须拥有\_\_\_\_个节点才能使树平衡。定义：一棵AVL树是指其中每个节点的左子树和右子树的高度最多相差1的二叉查找树。所以在高度为$h​$的AVL树种，最少节点数$S(h)=S(h-1)+S(h-2)+1​$给出。对于$h=0,S(h)=1;h=1,S(h)=2​$，所以对于一棵高度为$4​$的AVL树，最少可由\_\_\_\_个节点构成。

#### 二、解答题（共5题，每题10分。共50分）

1. 请实现填空题1中对二叉查找树节点的删除的方法（可用伪代码）。








2. 请画出将$2，1，4，5，9，3，6，7$插入到初始为空的AVL树中的每一个中间过程树。







3. 写出实现AVL树单旋转和双旋转的方法（可用伪代码）。







4. 编写一个递归方法，该方法使用对树$T$ 根节点的引用而返回从$T$ 删除所有叶节点所得到的对树的根节点的引用（可用伪代码）。








5. 编写一下高效的方法，只使用对二叉树$T$的根的引用，并计算（可用伪代码）：

   (1). $T$ 中节点的个数。

   (2). $T$ 中树叶的个数。

   (3). $T$ 中满节点的个数。



