package structure.map;

/**
 * Created by boyce on 2014/8/22.
 */
public abstract class IAbstractMap<K, V> implements IMap<K, V> {
    protected int size;
    protected float loadFactor;

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }
}
