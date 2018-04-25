package cce;

import common.utils.ArrayUtils;

import java.util.Comparator;

/**
 * Created by Boyce on 18/4/25.
 */
public class Arrays {
    public static <T> T max(T[] target, Comparator<T> comparator) {
        if (null == target || target.length == 0) return null;

        T max = target[0];
        for (T t: target)
            if (comparator.compare(max, t) < 0)  max = t;
        return max;
    }

    public static Integer sum(Integer[] target) {
        if (null == target || target.length == 0) return 0;

        Integer sum = 0;
        for (int i: target) sum += i;
        return sum;
    }

    public static double average(Integer[] target) {
        Integer sum = sum(target);
        return sum == 0 ? 0 : sum / target.length;
    }

    public static <T> T[] forward(T[] target) {
        if (null == target || target.length == 0) return target;

        T tmp = target[0];
        for (int i=0; i < target.length; i++) {
            int forward = i + 1;
            target[i] = forward == target.length ? tmp : target[forward];
        }
        return target;
    }

    public static void main(String[] args) {
        Integer[] target = {2, 4, 5, 3, 6, 1, 8, 9, 0, 7};
        int max = Arrays.max(target, new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });


        System.out.println("max = [" + max + "]");
        System.out.println("average = [" + average(target) + "]");
        System.out.println("forward(1) = [" + ArrayUtils.toString(forward(target)) + "]");
    }
}
