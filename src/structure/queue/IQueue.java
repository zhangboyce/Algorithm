package structure.queue;

import java.util.Queue;

/**
 * Created by boyce on 2014/7/16.
 */
public interface IQueue<T> {

    public void offer(T t) ;

    public T poll() ;

    public T peek() ;

    public int size() ;

    public boolean isEmpty() ;

    public void clear() ;

    public int indexOf(T t);

    public boolean contains(T t);

    public void addAll(IQueue<T> queue);

}
