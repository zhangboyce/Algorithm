package algorithm.sort;

import common.utils.ArrayUtils;

import java.util.Comparator;

/**
 * Created by boyce on 2014/8/14.
 */
public class ShellSorting<T extends Comparable> extends AbstractSorting<T> {

    @Override
    public void sort(T[] target, Comparator<T> comparator) {
        int d = target.length;
        while(true){
            d = d / 2;
            for(int x=0;x<d;x++){
                for(int i=x+d;i<target.length;i=i+d){
                    T temp = target[i];
                    int j;
                    for(j=i-d; j>=0&&comparator.compare(target[j], temp)>0; j=j-d)
                        target[j+d] = target[j];

                    target[j+d] = temp;
                }
            }
            if(d == 1){
                break;
            }
        }
    }

    public static void main(String[] args) {
        Integer[] target = {2, 4, 5, 3, 6, 1, 8, 9, 0, 7};

        Sorting sorting = new ShellSorting();
        sorting.sortAscending(target);
        System.out.println(ArrayUtils.toString(target));

        sorting.sortDescending(target);
        System.out.println(ArrayUtils.toString(target));
    }
}
