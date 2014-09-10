package algorithm.other;

import common.utils.ArrayUtils;

/**
 * Created by boyce on 2014/9/10.
 */
public class OrderStatistics<T extends Comparable> {

    /**
     * find min element from array
     * @param array
     * @return min element
     * list all array to find min element, O(n)
     */
    public T findMin(T[] array) {
        if (ArrayUtils.isEmpty(array))
            return null;

        if (array.length == 1)
            return array[0];

        T min = array[0];
        for (int i=1; i<array.length; i++)
            if (array[i].compareTo(min) < 0)
                min = array[i];

        return min;
    }

    /**
     * find max element from array
     * @param array
     * @return max element
     * list all array to find max element, O(n)
     */
    public T findMax(T[] array) {
        if (ArrayUtils.isEmpty(array))
            return null;

        if (array.length == 1)
            return array[0];

        T max = array[0];
        for (int i=1; i<array.length; i++)
            if (array[i].compareTo(max) > 0)
                max = array[i];

        return max;
    }

    public T findArticleN(T[] array, int n) {
        if (n > array.length)
            throw new ArrayIndexOutOfBoundsException("");

        return this.findArticleN(array, 0, array.length-1, n);
    }

    /**
     * find article n element from array
     * @param array resource array
     * @param p from p index in array
     * @param q end q index in array
     * @param i article i
     * @return
     */
    private T findArticleN(T[] array, int p, int q, int i) {
        if (p == q)
            return array[p];

        int t = this.partition(array, p, q);
        if (t == i-1)
            return array[t];

        else if (t < i-1)
            return findArticleN(array, t+1, q, i-t-1);

        else
            return findArticleN(array, p, t-1, i);
    }

    private int partition(T[] array, int p, int q) {
        T x = array[q];
        int i = p -1;
        for (int j=p; j<q; j++) {
            // if the target[j] < x , swap the index ++i element and index j element
            if (array[j].compareTo(x) < 0) {
                this.swap(array, ++i, j);

                // if the target[j] >= x, do nothing, continue j++ to handle the next element
            } else
                ;

        }
        this.swap(array, i+1, q);

        return i+1;
    }

    private void swap(T[] target, int p, int q) {
        T temp = target[p];
        target[p] = target[q];
        target[q] = temp;
    }

    public static void main(String[] args) {
        OrderStatistics<Integer> orderStatistics = new OrderStatistics<Integer>();
        Integer[] array = {2, 3, 11, 4, 6, 1, 0, 13, 7, 9, 12};

//        System.out.println(orderStatistics.findMax(array));
//        System.out.println(orderStatistics.findMin(array));
//        System.out.println(orderStatistics.findArticleN(array, 1));
//        System.out.println(orderStatistics.findArticleN(array, 11));
        System.out.println(orderStatistics.findArticleN(array, 5));
    }
}
