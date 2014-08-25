package structure.map;

import structure.list.IIterator;

import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by boyce on 2014/8/25.
 */
public class IHashTable<K, V> extends IAbstractMap<K, V> {

    protected int size;
    protected TableEntry<K, V>[] entries;
    protected float loadFactor;

    private final static int DEFAULT_CAPACITY =  16 ;
    private static final int MAXIMUM_CAPACITY = 1 << 30;
    private final static float DEFAULT_LOAD_FACTOR = 0.5f;

    public IHashTable() {
        this(DEFAULT_CAPACITY);
    }

    public IHashTable(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    public IHashTable(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                    initialCapacity);
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                    loadFactor);

        // Find a power of 2 >= initialCapacity
        int capacity = 1;
        while (capacity < initialCapacity)
            capacity <<= 1;

        this.loadFactor = loadFactor;
        entries = new TableEntry[capacity];
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean containsKey(K key) {
        if (null == key) return false;

        int hashCode = key.hashCode();
        int index = this.indexFor(hashCode, entries.length);
        TableEntry entry = this.entries[index];

        return entry != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (null == value) return false;

        for (TableEntry entry: entries) {
            for (; entry != null; entry = entry.next)
                if (entry.getValue().equals(value))
                    return true;
        }
        return false;
    }

    @Override
    public V get(K key) {
        if (null == key) return null;

        int hashCode = key.hashCode();
        int index = this.indexFor(hashCode, entries.length);
        TableEntry entry = entries[index];

        if (null == entry) return null;
        for (; entry != null; entry = entry.next)
            if (entry.getKey().equals(key))
                return (V)entry.getValue();

        return null;
    }

    @Override
    public void put(K key, V value) {
        if (key == null || value == null)
            return;

        if (this.size >= this.entries.length * this.loadFactor);
            this.resize(this.entries.length << 1);

        int hashCode = key.hashCode();
        int i = this.indexFor(hashCode, entries.length);

        for(TableEntry e = this.entries[i]; e!= null; e = e.next) {
            if (e.getKey().equals(key)) {
                e.value = value;
                return;
            }
        }

        this.entries[i] = new TableEntry(hashCode, key, value, this.entries[i]);
        this.size ++;

    }

    @Override
    public void remove(K key) {
        if (null == key) return;

        int hashCode = key.hashCode();
        int i = this.indexFor(hashCode, this.entries.length);

        TableEntry e = this.entries[i];
        TableEntry prev = e;
        while(null != e) {
            TableEntry<K,V> next = e.next;
            if (e.getKey().equals(key)) {
                this.size --;
                if (prev == e)
                    entries[i] = next;
                else
                    prev.next = next;
            }
            prev = e;
            e = next;
        }
    }

    @Override
    public void clear() {
        this.size = 0;
        this.entries = new TableEntry[DEFAULT_CAPACITY];
    }

    public String toString() {
        IIterator<IEntry<K,V>> i = new EntryIterator();
        if (! i.hasNext())
            return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (;;) {
            IEntry<K,V> e = i.next();
            K key = e.getKey();
            V value = e.getValue();
            sb.append(key   == this ? "(this Map)" : key);
            sb.append('=');
            sb.append(value == this ? "(this Map)" : value);
            if (! i.hasNext())
                return sb.append('}').toString();
            sb.append(',').append(' ');
        }
    }

    private final class ValueIterator extends TableIterator<V> {
        public V next() {
            return nextEntry().value;
        }
    }

    private final class KeyIterator extends TableIterator<K> {
        public K next() {
            return nextEntry().getKey();
        }
    }

    private final class EntryIterator extends TableIterator<IEntry<K,V>> {
        public IEntry<K,V> next() {
            return nextEntry();
        }
    }

    /**
     * abstract hash iterator
     */
    private abstract class TableIterator<E> implements IIterator<E> {
        TableEntry<K,V> next;
        int index;
        TableEntry<K,V> current;

        TableIterator() {
            if (IHashTable.this.size > 0) {
                TableEntry[] t = entries;

                //find the first element in array
                while (index < t.length && (next = t[index++]) == null)
                    ;
            }
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        public TableEntry<K, V> nextEntry() {
            TableEntry<K,V> e = next;
            if (e == null)
                throw new NoSuchElementException();

            if ((next = e.next) == null) {
                TableEntry[] t = entries;
                //find next element from array
                while (index < t.length && (next = t[index++]) == null)
                    ;
            }
            current = e;
            return e;
        }

        @Override
        public void remove() {
        }
    }

    /**
     * Hash entry
     * @param <K>
     * @param <V>
     */
    private static class TableEntry<K,V> implements IEntry<K,V> {
        final K key;
        V value;
        int hash;
        boolean isActive = true;

        TableEntry(int h, K k, V v) {
            value = v;
            key = k;
            hash = h;
        }

        public final K getKey() {
            return key;
        }

        public final V getValue() {
            return value;
        }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean isActive) {
            this.isActive = isActive;
        }

        public final boolean equals(Object o) {
            if (!(o instanceof IMap.IEntry))
                return false;
            IMap.IEntry e = (IMap.IEntry)o;
            Object k1 = getKey();
            Object k2 = e.getKey();
            if (k1 == k2 || (k1 != null && k1.equals(k2))) {
                Object v1 = getValue();
                Object v2 = e.getValue();
                if (v1 == v2 || (v1 != null && v1.equals(v2)))
                    return true;
            }
            return false;
        }

        public final int hashCode() {
            return (key==null   ? 0 : key.hashCode()) ^
                    (value==null ? 0 : value.hashCode());
        }

        public final String toString() {
            return getKey() + "=" + getValue();
        }

    }

    // return index through hash code
    private int indexFor(int h, int length) {
        return h & (length - 1);
    }

    // resize
    private void resize(int newCapacity) {
        TableEntry[] oldTable = entries;
        int oldCapacity = oldTable.length;
        if (oldCapacity == MAXIMUM_CAPACITY) {
            return;
        }

        TableEntry[] newTable = new TableEntry[newCapacity];
        for (TableEntry<K,V> e : oldTable) {
            while(null != e) {
                TableEntry<K,V> next = e.next;
                int i = indexFor(e.hash, newCapacity);
                e.next = newTable[i];
                newTable[i] = e;
                e = next;
            }
        }

        entries = newTable;
    }

    public static void main(String[] args) {
        IMap<String, String> map = new IHashTable<String, String>();
        map.put("1", "a");
        map.put("2", "b");
        map.put("3", "c");
        map.put("4", "d");

        System.out.println(map);

        map.put("4", "f");
        System.out.println(map);

        map.remove("4");
        System.out.println(map);
    }
}
