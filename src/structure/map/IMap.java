package structure.map;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by boyce on 2014/8/22.
 */
public interface IMap<K, V> {

    int size();

    boolean isEmpty();

    boolean containsKey(K key);

    boolean containsValue(V value);

    V get(K key);

    void put(K key, V value);

    void remove(K key);

    void clear();

    /**
     *  entry
     * @param <K>
     * @param <V>
     */
    interface IEntry<K,V> {

        K getKey();

        V getValue();

        V setValue(V value);
    }
}
