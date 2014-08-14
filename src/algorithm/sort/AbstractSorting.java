package algorithm.sort;

import common.utils.ArrayUtils;

import java.util.Comparator;

/**
 * Created by boyce on 2014/8/14.
 */
public abstract class AbstractSorting<T extends Comparable> implements Sorting<T> {

    @Override
    public void sortAscending(T[] target) {
        if (ArrayUtils.isEmpty(target))
            return;

        this.sort(target, new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return o1.compareTo(o2);
            }
        });
    }

    @Override
    public void sortDescending(T[] target) {
        if (ArrayUtils.isEmpty(target))
            return;

        this.sort(target, new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return -o1.compareTo(o2);
            }
        });
    }
}
