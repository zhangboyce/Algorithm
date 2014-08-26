package structure.map;

import structure.list.IIterator;

import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by boyce on 2014/8/25.
 */
public class IHashTable<K, V> extends IAbstractMap<K, V> {

    protected TableEntry<K, V>[] entries;

    private final static int DEFAULT_CAPACITY =  16 ;
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
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                    loadFactor);

        int capacity = 1;
        while (capacity < initialCapacity)
            capacity <<= 1;

        this.loadFactor = loadFactor;
        entries = new TableEntry[capacity];
    }

    @Override
    public boolean containsKey(K key) {
        return this.isActive(this.findIndex(key, entries));
    }

    @Override
    public boolean containsValue(V value) {
        IIterator<TableEntry<K,V>> iIterator = new EntryIterator();

        TableEntry<K,V> entry = null;
        while (iIterator.hasNext()) {
            entry = iIterator.next();
            if (entry.isActive() && entry.getValue().equals(value))
                return true;
        }
        return false;
    }

    @Override
    public V get(K key) {
        int index = this.findIndex(key, entries);
        if (this.isActive(index))
            return this.entries[index].getValue();

        return null;
    }

    @Override
    public void put(K key, V value) {
        if (key == null || value == null)
            return;

        if (this.size >= this.entries.length * this.loadFactor)
            this.resize(this.entries.length << 1);

        int index = this.findIndex(key, entries);
        this.entries[index] = new TableEntry(key.hashCode(), key, value);

        this.size ++;

    }

    @Override
    public void remove(K key) {
        if (null == key) return;

        int index = this.findIndex(key, entries);
        if (this.isActive(index)) {
            this.entries[index].setActive(false);
            this.size -- ;
        }
    }

    @Override
    public void clear() {
        this.size = 0;
        this.entries = new TableEntry[DEFAULT_CAPACITY];
    }

    public String toString() {
        if (this.isEmpty())
            return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (int i=0; i<this.entries.length; i++)
            if (this.isActive(this.entries[i])) {
                sb.append(this.entries[i].key);
                sb.append('=');
                sb.append(this.entries[i].value);

                sb.append(',').append(' ');
            }


        sb.delete(sb.length()-2, sb.length());
        return sb.append('}').toString();
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

    private final class EntryIterator extends TableIterator<TableEntry<K,V>> {
        public TableEntry<K,V> next() {
            return nextEntry();
        }
    }

    /**
     * abstract hash iterator
     */
    private abstract class TableIterator<E> implements IIterator<E> {
        TableEntry<K,V> next;
        int index;

        TableIterator() {
            if (IHashTable.this.size > 0) {
                //find the first element in array
                while (index < entries.length && !IHashTable.this.isActive(next = entries[index++]))
                    ;
            }
        }

        @Override
        public boolean hasNext() {
            return IHashTable.this.isActive(next);
        }

        public TableEntry<K, V> nextEntry() {
            TableEntry<K,V> e = next;
            if (!hasNext())
                throw new NoSuchElementException();

            // modify next element
            while (index < entries.length && !IHashTable.this.isActive(next = entries[index++]))
                ;

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

    // f(i)=i*i  <---> f(i)=(i-1)*(i-1) + 2*i - 1 = f(i-1) + 2*i - 1
    private int findIndex(K key, TableEntry[] entries) {
        if (key == null) return -1;

        int hashCode = key.hashCode();
        int currentPos = hashCode & (entries.length - 1);

        int offset = 1;
        //current slot is not null or current slot element key not equals key,
        //continue find the next position
        while (entries[currentPos] != null &&
                !entries[currentPos].getKey().equals(key)) {

            currentPos += offset;
            offset += 2;

            if (currentPos >= entries.length)
                currentPos -= entries.length;
        }
        return currentPos;
    }

    private boolean isActive(int index) {
        if (index < 0 || index >= this.entries.length)
            return false;

        return this.isActive(this.entries[index]);
    }

    private boolean isActive(TableEntry entry) {
        return null != entry && entry.isActive();
    }

    // resize
    private void resize(int newCapacity) {
        TableEntry[] oldEntries = this.entries;
        this.entries = new TableEntry[newCapacity];
        this.size = 0;

        for (int i=0; i<oldEntries.length; i++)
            if (this.isActive(oldEntries[i]))
                this.put((K)oldEntries[i].key, (V)oldEntries[i].value);
    }

    public static void main(String[] args) {
        IMap<String, String> map = new IHashTable<String, String>(4);
        map.put("one", "1");
        map.put("two", "2");
        map.put("three", "3");
        map.put("four", "4");
        map.put("five", "5");
        map.put("six", "6");
        map.put("seven", "7");
        map.put("eight", "8");
        map.put("nine", "9");
        map.put("ten", "10");

        System.out.println(map);

        map.put("seven", "七");
        System.out.println(map);

        map.remove("ten");
        System.out.println(map);
    }
}
