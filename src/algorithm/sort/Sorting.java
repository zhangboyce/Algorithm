package algorithm.sort;

import java.util.Comparator;

/**
 * Created by boyce on 2014/8/14.
 */
public interface Sorting<T extends Comparable> {

    /**
     * ascending sort
     * @param target
     * @return
     */
    public void sortAscending(T[] target);

    /**
     * descending sort
     * @param target
     * @return
     */
    public void sortDescending(T[] target);

    /**
     * sort by self comparator
     * @param target
     * @param comparator
     */
    public void sort(T[] target, Comparator<T> comparator);
}
