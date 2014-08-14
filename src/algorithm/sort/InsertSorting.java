package algorithm.sort;

import common.utils.ArrayUtils;

import java.util.Comparator;

/**
 * Created by boyce on 2014/8/14.
 */
public class InsertSorting<T extends Comparable> extends AbstractSorting<T> {

    @Override
    public void sort(T[] target, Comparator<T> comparator) {
        for (int i = 1; i < target.length; i++) {
            T temp = target[i];
            int j;
            for (j = i-1; j>=0; j--) {
                if(comparator.compare(target[j], temp) > 0){
                    target[j+1] = target[j];
                }else{
                    break;
                }
            }
            target[j+1] = temp;
        }
    }

    public static void main(String[] args) {
        Integer[] target = {2, 4, 5, 3, 6, 1, 8, 9, 0, 7};

        Sorting sorting = new InsertSorting();
        sorting.sortAscending(target);
        System.out.println(ArrayUtils.toString(target));

        sorting.sortDescending(target);
        System.out.println(ArrayUtils.toString(target));
    }
}
