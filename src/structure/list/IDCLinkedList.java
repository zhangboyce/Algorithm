package structure.list;

/**
 * Created by boyce on 2014/7/6.
 */
public class IDCLinkedList<T> implements IList<T> {

    private Node head;
    private int size;

    public IDCLinkedList() {
        this.head = new Node();
        this.head.next = this.head;
        this.head.prior = this.head;
        this.size = 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    /**
     * get node index i-1
     * @param i index
     * @return node object
     */
    private Node index(int i) {
        if (i < 0 || i > this.size)
            throw new IllegalArgumentException("illegal index: " + i);

        Node p = null;
        if (i == 0)
            p = this.head;
        else {
            p = this.head.next;
            int j = 1;
            while (j < i && p != this.head) {
                p = p.next;
                j ++;
            }
        }
        return p;
    }

    @Override
    public boolean contains(T t) {
        return this.indexOf(t) != -1;
    }

    @Override
    public void insert(int i, T t) {

        //i=0 return head, i=1 return head.next(index=0) and so on.
        //So, actually, get the Node index i-1
        Node p = this.index(i);

        Node n = new Node(t);
        p.next.prior = n;
        n.next = p.next;
        n.prior = p;
        p.next = n;

        size ++;
    }

    @Override
    public void add(T t) {
        this.insert(this.size, t);
    }

    @Override
    public T remove(int i) {

        Node p = this.index(i);
        Node removed = p.next;

        p.next = p.next.next;
        p.next.prior = p;

        size --;

        return (T)removed.data;
    }

    @Override
    public T remove(T t) {
        int i = this.indexOf(t);
        if (i == -1) return null;

        return this.remove(i);
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public T get(int i) {
        Node p = this.index(i);
        if (p == null) return null;

        return (T)p.next.data;
    }

    @Override
    public int indexOf(T t) {
        Node p = head.next;
        int j = 0;

        while (p != head && !p.data.equals(t)) {
            p = p.next;
            j ++;
        }

        if (p != null) return j;
        return -1;
    }

    @Override
    public void clear() {
        this.size = 0;
        this.head.next = this.head;
        this.head.prior = this.head;
    }

    @Override
    public IIterator iterator() {
        return new IDCLinkedListIterator();
    }

    private class IDCLinkedListIterator<T> implements IIterator<T> {

        private int current;

        @Override
        public boolean hasNext() {
            return this.current < IDCLinkedList.this.size();
        }

        @Override
        public T next() {
            return (T)IDCLinkedList.this.index(current ++);
        }

        @Override
        public void remove() {
            IDCLinkedList.this.remove(current --);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");

        Node p = head.next;
        while (p != head) {
            builder.append(p.data.toString()).append(", ");
            p = p.next;
        }

        builder.delete(builder.length()-2, builder.length());
        builder.append("]");

        return builder.toString();
    }

    private static class Node<T> {
        private Node next;
        private Node prior;
        private T data;

        public Node(T data) {
            this.data = data;
        }

        public Node() {
            this(null);
        }
    }
}
