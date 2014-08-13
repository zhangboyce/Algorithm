package structure.tree;

/**
 * Created by boyce on 2014/8/13.
 */
public class ISplayingTree<T extends Comparable> extends IAbstractTree<T> {

    @Override
    protected int deep(Node node) {
        return 0;
    }

    @Override
    protected T findMax(Node node) {
        return null;
    }

    @Override
    protected T findMin(Node node) {
        return null;
    }

    @Override
    protected boolean contains(T t, Node node) {
        return false;
    }

    @Override
    protected Node insert(T element, Node node) {
        return null;
    }

    @Override
    protected Node remove(T element, Node node) {
        return null;
    }
}
