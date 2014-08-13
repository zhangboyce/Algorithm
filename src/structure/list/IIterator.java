package structure.list;

/**
 * Created by boyce on 2014/7/14.
 */
public interface IIterator<T> {
    public boolean hasNext();
    public T next();
    public void remove();
}
