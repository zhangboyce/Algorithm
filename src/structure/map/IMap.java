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

    boolean containsKey(Object key);

    boolean containsValue(Object value);

    V get(Object key);

    V put(K key, V value);

    V remove(Object key);

    void putAll(Map<? extends K, ? extends V> m);

    void clear();

    Set<K> keySet();

    Collection<V> values();

    Set<IMap.IEntry<K, V>> entrySet();

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
