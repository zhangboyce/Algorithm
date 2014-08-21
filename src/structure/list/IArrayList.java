package structure.list;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by boyce on 2014/7/6.
 */
public class IArrayList<T> implements IList<T> {

    private static final int CAPACITY_OFFSET = 10;
    private static final int DEFAULT_CAPACITY = 10;

    private Object[] elements;
    private int size;

    public IArrayList(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException("Cannot construct a list, capacity = " + capacity);

        this.elements = new Object[capacity];
    }

    public IArrayList() {
        this(DEFAULT_CAPACITY);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void insert(int i, T t) {
        if (i < 0 || i > this.size)
            throw new IllegalArgumentException("Illegal insert indexOf, " + i);

        if(this.beFull())
            this.ensureCapacity();

        for (int j=this.size; j>i; j--)
            this.elements[j] = this.elements[j-1];

        this.elements[i] = t;
        this.size ++;
    }

    @Override
    public void add(T t) {
        this.insert(this.size, t);
    }

    @Override
    public T remove(int i) {
        if (i < 0 || i > this.size-1)
            throw new IllegalArgumentException("Illegal remove indexOf, " + i);

        if (this.size == 0)
           return null;

        T t = this.get(i);

        // 0 1 2 3 4 5 6
        for (int j=i; j<this.size; j++)
            this.elements[j] = this.elements[j+1];

        this.size --;
        return t;
    }

    @Override
    public T remove(T t) {
        int i = this.indexOf(t);
        if (i < 0) return null;

        T t1 = this.remove(i);
        return t1;
    }

    @Override
    public boolean contains(T t) {
        return this.indexOf(t) != -1;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public T get(int i) {
        if (i < 0 || i > this.size-1)
            throw new IllegalArgumentException("Illegal get indexOf, " + i);

        return (T)this.elements[i];
    }

    @Override
    public int indexOf(T t) {

        for (int i=0; i<this.size; i++) {
            if (this.elements[i].equals(t))
                return i;
        }

        return -1;
    }

    @Override
    public void clear() {
        this.size = 0;
        this.elements = new Object[DEFAULT_CAPACITY];
    }

    @Override
    public IIterator iterator() {
        return new IArrayListIterator();
    }

    private class IArrayListIterator<T> implements IIterator<T> {

        private int current;

        @Override
        public boolean hasNext() {
            return current < IArrayList.this.size();
        }

        @Override
        public T next() {
            return (T)elements[current ++] ;
        }

        @Override
        public void remove() {
            IArrayList.this.remove(current --);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");

        for (int i=0; i<this.size; i++)
            builder.append(this.elements[i].toString()).append(", ");

        if (builder.length() > 3)
            builder.delete(builder.length()-2, builder.length());
        builder.append("]");

        return builder.toString();
    }

    private boolean beFull() {
        return this.size + 1 == this.elements.length;
    }

    private void ensureCapacity() {
        Object[] newElements = new Object[this.elements.length + CAPACITY_OFFSET];
        for (int i=0; i<this.elements.length; i++)
            newElements[i] = elements[i];

        this.elements = newElements;
    }
}
