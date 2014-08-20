package structure.queue.priority.dynamic;

import common.utils.ObjectUtils;
import structure.queue.priority.IPriorityQueue;

/**
 * Created by boyce on 2014/8/19.
 */
public interface IDynamicPriorityQueue extends IPriorityQueue<Integer> {


    /**
     * decrease offset value index i
     * @param i
     */
    public void decrease(int i, Integer offset);

    /**
     * increase offset value index i
     * @param i
     */
    public void increase(int i, Integer offset);

    public void delete(int i);

}
