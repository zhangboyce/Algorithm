package algorithm.sort;

import common.utils.ArrayUtils;

import java.util.Comparator;

/**
 * Created by boyce on 2014/8/14.
 */
public class SelectSorting<T extends Comparable> extends AbstractSorting<T> {

    @Override
    public void sort(T[] target, Comparator<T> comparator) {
        for (int i = 0; i < target.length; i++) {
            T min = target[i];
            int n=i;
            for(int j=i+1;j<target.length;j++){

                if(comparator.compare(target[j], min) < 0){
                    min = target[j];
                    n = j;
                }
            }
            target[n] = target[i];
            target[i] = min;

        }
    }

    public static void main(String[] args) {
        Integer[] target = {2, 4, 5, 3, 6, 1, 8, 9, 0, 7};

        Sorting sorting = new SelectSorting();
        sorting.sortAscending(target);
        System.out.println(ArrayUtils.toString(target));

        sorting.sortDescending(target);
        System.out.println(ArrayUtils.toString(target));
    }
}
