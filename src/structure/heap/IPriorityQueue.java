package structure.heap;

import structure.queue.IQueue;

/**
 * Created by boyce on 2014/8/13.
 */
public interface IPriorityQueue extends IQueue<Integer> {
    public void display();

    /**
     * decrease offset value index i
     * @param i
     * @param offset
     */
    public void decrease(int i, Integer offset);

    /**
     * increase offset value index i
     * @param i
     * @param offset
     */
    public void increase(int i, Integer offset);

    public void delete(int i);
}
