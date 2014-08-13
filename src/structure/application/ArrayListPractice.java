package structure.application;

import common.utils.ArrayUtils;
import common.utils.CollectionUtils;
import structure.list.IArrayList;
import structure.list.ILinkedList;
import structure.list.IList;

import java.util.ArrayList;

/**
 * Created by boyce on 2014/7/28.
 */
public class ArrayListPractice {

    public static void main(String[] args) {
        Integer[] a1 = {1, 3, 2, 5, 7, 3, 4, 9, 0, 6};
        Integer[] a2 = {4, 3, 5, 0, 3};

        a1 = ArrayUtils.quickSort(a1);
        a2 = ArrayUtils.quickSort(a2);

        Integer[] i = ArrayListPractice.intersection(a1, a2);
        Integer[] u = ArrayListPractice.union(a1, a2);

        System.out.println(ArrayUtils.toString(a1));
        System.out.println(ArrayUtils.toString(a2));
        System.out.println(ArrayUtils.toString(i));
        System.out.println(ArrayUtils.toString(u));
    }

    /**
     * 求两个数组的交集
     * @param a1
     * @param a2
     * @return
     */
    public static Integer[] intersection(Integer[] a1, Integer[] a2) {

        IList<Integer> in = new IArrayList<Integer>();

        int i = 0;
        int j = 0;

        while (i<a1.length && j<a2.length) {
            if (a1[i] == a2[j]) {
                if (!in.contains(a1[i]))
                    in.add(a1[i]);

                i ++;
                j ++;
            } else if(a1[i] < a2[j])
                i ++;
            else
                j ++;

            if (i == a1.length || j == a2.length)
                break;
        }

        Integer[] result = new Integer[in.size()];
        for (int t=0; t<in.size(); t++)
            result[t] = in.get(t);

        return result;
    }

    /**
     * 求两个数组的交集
     * @param a1
     * @param a2
     * @return
     */
    public static Integer[] union(Integer[] a1, Integer[] a2) {

        IList<Integer> in = new IArrayList<Integer>();

        int i = 0;
        int j = 0;

        while (i<a1.length && j<a2.length) {
            if (a1[i] != a2[j]) {
                if (!in.contains(a1[i]))
                    in.add(a1[i]);

                if (!in.contains(a2[j]))
                    in.add(a2[j]);

                i ++;
                j ++;
            }
            else {
                if (!in.contains(a1[i]))
                    in.add(a1[i]);

                i ++;
                j ++;
            }
            if (i == a1.length) {
                while (j<a2.length) {
                    if (!in.contains(a2[j]))
                        in.add(a2[j]);
                    j++;
                }
            }

            if (j == a2.length) {
                while (i<a1.length) {
                    if (!in.contains(a1[i]))
                        in.add(a1[i]);
                    i ++;
                }
            }
        }

        Integer[] result = new Integer[in.size()];
        for (int t=0; t<in.size(); t++)
            result[t] = in.get(t);

        return result;
    }
}
