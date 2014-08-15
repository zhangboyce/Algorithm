package structure.heap;

import structure.queue.IQueue;

/**
 * Created by boyce on 2014/8/13.
 */
public interface IPriorityQueue<T extends Comparable> extends IQueue<T> {

    public void display();
}
