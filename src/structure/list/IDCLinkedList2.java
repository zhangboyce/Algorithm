package structure.list;

/**
 * Created by boyce on 2014/7/16.
 */
public class IDCLinkedList2<T>{

    private Node head;
    private Node tail;
    private int size;

    public IDCLinkedList2() {
        this.head = new Node();
        this.tail = new Node();
        this.head.next = this.tail;
        this.head.prior = this.tail;
        this.tail.next = this.head;
        this.tail.prior = this.head;
    }

    public void add(T t) {
        this.addBefore(size, t);
    }

    public void addFirst(T t) {
        this.addAfter(-1, t);
    }

    public void addBefore(int i, T t) {
        Node p = this.index(i);
        Node n = new Node(t);

        p.prior.next = n;
        n.next = p;

        n.prior = p.prior;
        p.prior = n;

        this.size ++;
    }

    public void addAfter(int i, T t) {
        Node p = this.index(i);
        Node n = new Node(t);

        p.next.prior = n;
        n.prior = p;

        n.next = p.next;
        p.next = n;

        this.size ++;
    }

    public void exchange(int i, int j) {
        if (i < 0 || i >= size)
            throw new IllegalArgumentException("Illegal index : " + i);

        if (i < 0 || i >= size)
            throw new IllegalArgumentException("Illegal index : " + i);

        if (i == j) return;

        Node a = this.index(i);
        Node b = this.index(j);

        Object temp = a.data;
        a.data = b.data;
        b.data = temp;
    }

    public void reverse() {
        if (this.size < 2) return;

        int i=0;
        int j = this.size -1;
        for (; i<this.size/2; i++, j--) {
            this.exchange(i, j);
        }
    }

    public int indexOf(T t) {
        return 0;
    }

    /**
     *
     * @param i -1(head), size(tail)
     * @return
     */
    private Node index(int i) {
        if (i < -1 || i > this.size)
            throw new IllegalArgumentException("Illegal index : " + i);

        if (i == -1)
            return this.head;
        if (i == size)
            return this.tail;

        Node p = this.head;
        int j = -1;
        while (p.next != tail && j < i) {
            p = p.next;
            j ++;
        }
        return p;
    }

    public int size() {
        return this.size;
    }



    public void clear() {
        this.size = 0;
        this.head.next = this.tail;
        this.head.prior = this.tail;
        this.tail.next = this.head;
        this.tail.prior = this.head;
    }

    private static class Node<T> {
        private Node next;
        private Node prior;
        private T data;

        private Node(Node next, T data) {
            this.data = data;
            this.next = next;
        }

        private Node(T data) {
            this(null, data);
        }

        private Node() {
            this(null, null);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        Node p = this.head;
        while (p.next != this.tail) {
            p = p.next;
            builder.append(p.data).append(", ");
        }

        if (builder.length() > 2)
            builder.delete(builder.length()-2, builder.length());

        return builder.append("]").toString();
    }

    public static void main(String[] args) {
        IDCLinkedList2 list = new IDCLinkedList2();
        list.add("d");
        list.add("e");
        list.addFirst("c");
        list.addFirst("b");
        list.addFirst("a");

        list.reverse();
        list.reverse();

        System.out.println(list);
    }
}
