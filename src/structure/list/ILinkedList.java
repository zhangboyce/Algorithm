package structure.list;

/**
 * Created by boyce on 2014/7/6.
 */
public class ILinkedList<T> implements IList<T> {

    protected Node head;
    protected int size;

    public ILinkedList() {
        this.head = new Node();
    }

    @Override
    public int size() {
        return this.size;
    }

    /**
     * Get indexOf i-1:
     * i=0 return head, i=1, return head.next(indexOf=0)
     */
    private Node index(int i) {
        if (i < 0 || i > this.size)
            throw new IllegalArgumentException("Illegal indexOf, " + i);

        Node p = head;
        int j = 0;
        while (p != null && j < i) {
            p = p.next;
            j ++;
        }

        return p;
    }

    @Override
    public void insert(int i, T t) {
        Node p = this.index(i);
        Node n = new Node(t);

        n.next = p.next;
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

        Node n = p.next;
        p.next = p.next.next;
        size --;

        return (T)n.data;
    }

    @Override
    public T remove(T t) {
        int i = this.indexOf(t);
        T removed = this.remove(i);
        return removed;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public T get(int i) {
        Node p = this.index(i);

        return (T)p.data;
    }

    @Override
    public boolean contains(T t) {
        return this.indexOf(t) != -1;
    }

    @Override
    public int indexOf(T t) {
        Node p = head.next;
        int j = 0;

        while (p != null && !p.data.equals(t)) {
            p = p.next;
            j ++;
        }

        if (p != null) return j;
        return -1;
    }

    @Override
    public void clear() {
        this.size = 0;
        this.head.data = null;
        this.head.next = null;
    }

    @Override
    public IIterator iterator() {
        return new ILinkedListIterator();
    }

    private class ILinkedListIterator<T> implements IIterator<T> {

        private int current;

        @Override
        public boolean hasNext() {
            return this.current < ILinkedList.this.size();
        }

        @Override
        public T next() {
            return (T)ILinkedList.this.index(current ++);
        }

        @Override
        public void remove() {
            ILinkedList.this.remove(current --);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");

        Node p = head.next;
        while (p != null) {
            builder.append(p.data.toString()).append(", ");
            p = p.next;
        }

        builder.delete(builder.length()-2, builder.length());
        builder.append("]");

        return builder.toString();
    }

    protected static class Node<T> {
        public T data;
        public Node next;

        public Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }

        public Node(T data) {

            this(data, null);
        }

        public Node() {
            this(null, null);
        }

    }
}
