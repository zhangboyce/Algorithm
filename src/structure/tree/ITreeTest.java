package structure.tree;

/**
 * Created by boyce on 2014/7/30.
 */
public class ITreeTest {
    public static void main(String[] args) {
        IBinaryTree tree = new IBinaryTree();
        tree.insert(8);
        tree.insert(3);
        tree.insert(6);
        tree.insert(1);
        tree.insert(4);
        tree.insert(2);
        tree.insert(7);
        tree.insert(5);

        System.out.println(tree.DLR());
        System.out.println(tree.LDR());
        System.out.println(tree.LRD());
        tree.display();

        System.out.println(tree.findMax());
        System.out.println(tree.findMin());
        System.out.println(tree.deep());

        IAVLTree iavlTree = new IAVLTree();

        ITree.Node _5 = new ITree.Node(5);
        ITree.Node _6 = new ITree.Node(6);
        ITree.Node _7 = new ITree.Node(7);
        ITree.Node _8 = new ITree.Node(8);

        _5.rightNode = _7;
        _7.rightNode = _8;
        _7.leftNode = _6;

        IAbstractTree.BTreePrinter.printNode(_5);
        IAbstractTree.BTreePrinter.printNode(iavlTree.rotateWithRightChild(_5));

        _5 = new ITree.Node(5);
        _6 = new ITree.Node(6);
        _7 = new ITree.Node(7);
        _8 = new ITree.Node(8);

        _8.leftNode = _6;
        _6.rightNode = _7;
        _6.leftNode = _5;

        IAbstractTree.BTreePrinter.printNode(_8);
        IAbstractTree.BTreePrinter.printNode(iavlTree.rotateWithLeftChild(_8));

        _5 = new ITree.Node(5);
        _6 = new ITree.Node(6);
        _7 = new ITree.Node(7);
        _8 = new ITree.Node(8);

        _5.rightNode = _8;
        _8.leftNode = _7;
        //_7.leftNode = _6;

        IAbstractTree.BTreePrinter.printNode(_5);
        IAbstractTree.BTreePrinter.printNode(iavlTree.doubleWithRightChild(_5));

        _5 = new ITree.Node(5);
        _6 = new ITree.Node(6);
        _7 = new ITree.Node(7);
        _8 = new ITree.Node(8);

        _8.leftNode = _5;
        _5.rightNode = _7;

        IAbstractTree.BTreePrinter.printNode(_8);
        IAbstractTree.BTreePrinter.printNode(iavlTree.doubleWithLeftChild(_8));
    }
}
