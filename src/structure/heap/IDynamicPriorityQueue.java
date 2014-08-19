package structure.heap;

import common.utils.ObjectUtils;

/**
 * Created by boyce on 2014/8/19.
 */
public interface IDynamicPriorityQueue<Integer> extends IPriorityQueue {


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
