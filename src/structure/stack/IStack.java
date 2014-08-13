package structure.stack;

/**
 * Created by boyce on 2014/7/16.
 */
public interface IStack<T> {
    public int size();

    public boolean isEmpty();

    public void push(T t);

    public T peek();

    public T pop();

    public void clear();
}
