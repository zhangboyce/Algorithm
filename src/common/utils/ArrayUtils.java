package common.utils;

import structure.list.IArrayList;
import structure.list.IList;

import java.util.Objects;

/**
 * Created by boyce on 2014/7/28.
 */
public abstract class ArrayUtils {

    public static void main(String[] args) {
        Integer[] array = {1, 3, 2, 4, 0};

        System.out.println(toString(quickSort(array)));
    }

    public static Integer[] quickSort(Integer[] array) {
        if (null == array || array.length == 0)
            return array;

        for (int i=1; i<array.length; i++) {
            int a = array[i];
            int j = i;
            while (j>0 && array[j-1]>a) {
                array[j] = array[j-1];
                j --;
            }
            array[j] = a;
        }

        return array;
    }

    public static <T> boolean isEmpty(T[] ts) {
        return ts == null || ts.length == 0;
    }

    public static <T> boolean isNotEmpty(T[] ts) {
        return !isEmpty(ts);
    }

    public static <T> IList<T> asList(T... ts) {
        if (isEmpty(ts))
            return new IArrayList<T>();

        IList<T> list = new IArrayList<T>();
        for (T t: ts) {
            list.add(t);
        }

        return list;
    }



    public static <T> String toString(T[] ts) {
        StringBuilder builder = new StringBuilder(ts.length * 2);
        builder.append("[");
        for (int i=0; i<ts.length; i++) {
            builder.append(ts[i].toString());
            if (i != ts.length -1)
                builder.append(", ");
        }

        builder.append("]");

        return builder.toString();
    }
}
