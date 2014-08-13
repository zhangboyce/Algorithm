package structure.application;

import structure.list.IArrayList;
import structure.list.IList;

import java.util.List;

/**
 * Created by boyce on 2014/7/14.
 */
public class Josephus {
    /**
     * n 个人编号从1到n，围坐一圈，从1开始传递一个热土豆，经过m次传递后拿到
     * 土豆的人清除，由被清除的后面的人拿着土豆继续传递，最后剩下的人取胜。
     *
     * 编写一个程序解决m和n在一般值下的Josephus问题。
     */

    public static void josephus(int n, int m) {
        IList<Integer> list = new IArrayList<Integer>(n);
        for (int i=0; i<n; i++)
            list.add(i);

        int begin = -1;
        while (n > 0) {
            begin += m;
            int removed = list.remove(begin % n);
            System.out.println("Remove: " + removed);

            begin = (begin % n) - 1;
            n --;
        }
    }

    public static void main(String[] args) {
        Josephus.josephus(5, 1);
    }
}
