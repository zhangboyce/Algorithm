package algorithm.sort;

import common.utils.ArrayUtils;
import java.util.Comparator;
/**
 * Created by boyce on 2014/8/14.
 */
public class BubbleSorting<T extends Comparable> extends AbstractSorting<T> {

    @Override
    public void sort(T[] target, Comparator<T> comparator) {
        int len = target.length;
        for(int i=0;i<len;i++) {
            for(int j=i+1;j<len;j++) {
                T temp;
                if(comparator.compare(target[i], target[j]) > 0) {
                    temp = target[j];
                    target[j] = target[i];
                    target[i] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        Integer[] target = {2, 4, 5, 3, 6, 1, 8, 9, 0, 7};

        Sorting sorting = new BubbleSorting();
        sorting.sortAscending(target);
        System.out.println(ArrayUtils.toString(target));

        sorting.sortDescending(target);
        System.out.println(ArrayUtils.toString(target));
    }
}
