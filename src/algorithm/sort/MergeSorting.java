package algorithm.sort;

import common.utils.ArrayUtils;

import java.util.Comparator;

/**
 * Created by boyce on 2014/8/14.
 */
public class MergeSorting<T extends Comparable> extends AbstractSorting<T> {

    private Comparator<T> comparator;

    @Override
    public void sort(T[] target, Comparator<T> comparator) {
        this.comparator = comparator;
        this.mergeSort(target, 0, target.length-1);
    }

    private void mergeSort(T[] target, int left, int right) {
        if(left<right){
            int middle = (left+right)/2;
            mergeSort(target, left, middle);
            mergeSort(target, middle+1, right);

            merge(target,left,middle,right);
        }
    }

    private void merge(T[] target, int left, int middle, int right) {
        T[] tmpArr = (T[])new Comparable[target.length];
        int mid = middle+1;
        int tmp = left;
        int third = left;
        while(left<=middle && mid<=right){
            if(comparator.compare(target[left], target[mid]) <= 0){
                tmpArr[third++] = target[left++];
            }else{
                tmpArr[third++] = target[mid++];
            }
        }

        while(left<=middle){
            tmpArr[third++] = target[left++];
        }
        while(mid<=right){
            tmpArr[third++] = target[mid++];
        }
        while(tmp<=right){
            target[tmp] = tmpArr[tmp++];
        }
    }


    public static void main(String[] args) {
        Integer[] target = {2, 4, 5, 3, 6, 1, 8, 9, 0, 7,10,-2,9,-3,12,8,3,5,-5,0};

        Sorting sorting = new MergeSorting();
        sorting.sortAscending(target);
        System.out.println(ArrayUtils.toString(target));

        sorting.sortDescending(target);
        System.out.println(ArrayUtils.toString(target));
    }
}
