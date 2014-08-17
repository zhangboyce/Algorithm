package structure.heap;

import structure.queue.IQueue;

/**
 * Created by boyce on 2014/8/13.
 */
public interface IPriorityQueue<T extends Comparable> extends IQueue<T> {
    public void display();

    /**
     * decrease offset value index i
     * @param i
     * @param offset
     */
    public void decrease(int i, T offset);

    /**
     * increase offset value index i
     * @param i
     * @param offset
     */
    public void increase(int i, T offset);

    public void delete(int i);
}
