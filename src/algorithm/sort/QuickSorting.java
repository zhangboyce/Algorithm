package algorithm.sort;

import common.utils.ArrayUtils;

import java.util.Comparator;

/**
 * Created by boyce on 2014/8/14.
 */
public class QuickSorting<T extends Comparable> extends AbstractSorting<T> {

    private Comparator<T> comparator;

    @Override
    public void sort(T[] target, Comparator<T> comparator) {
        this.comparator = comparator;
        this.quickSort(target, 0, target.length-1);
    }

    private void quickSort(T[] target, int low, int high) {
        if(low<high) {
            int middle = getMiddle(target, low, high);
            quickSort(target, 0, middle);
            quickSort(target, middle+1, high);
        }
    }

    private int getMiddle(T[] target, int low, int high) {
        T temp = target[low];
        while(low<high){
            while(low<high && comparator.compare(target[high], temp) >= 0){
                high --;
            }
            target[low] = target[high];
            while(low<high && comparator.compare(target[high], temp) <= 0){
                low ++;
            }
            target[high] = target[low];
        }
        target[low] = temp;
        return low;
    }

    public static void main(String[] args) {
        Integer[] target = {2, 4, 5, 3, 6, 1, 8, 9, 0, 7,10,-2,9,-3,12,8,3,5,-5,0};

        Sorting sorting = new QuickSorting();
        sorting.sortAscending(target);
        System.out.println(ArrayUtils.toString(target));

        sorting.sortDescending(target);
        System.out.println(ArrayUtils.toString(target));
    }
}
