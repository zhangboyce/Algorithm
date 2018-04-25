package algorithm.sort;

import common.utils.ArrayUtils;

import java.util.Comparator;

/**
 * Created by boyce on 2014/8/14.
 */
public class InsertDichotomySorting<T extends Comparable> extends AbstractSorting<T> {

    public void sort(T[] target, Comparator<T> comparator) {

        for (int i = 0; i < target.length; i++) {
            T temp = target[i];
            int left = 0;
            int right = i-1;
            int mid = 0;
            while(left<=right){
                mid = (left+right)/2;
                if(comparator.compare(target[mid], temp) > 0){
                    right = mid-1;
                }else{
                    left = mid+1;
                }
            }
            for (int j = i-1; j >= left; j--) {
                target[j+1] = target[j];
            }
            if(left != i){
                target[left] = temp;
            }
        }
    }

    public static void main(String[] args) {
        Integer[] target = {2, 4, 5, 3, 6, 1, 8, 9, 0, 7};

        Sorting sorting = new InsertDichotomySorting();
        sorting.sortAscending(target);
        System.out.println(ArrayUtils.toString(target));

        sorting.sortDescending(target);
        System.out.println(ArrayUtils.toString(target));
    }
}
