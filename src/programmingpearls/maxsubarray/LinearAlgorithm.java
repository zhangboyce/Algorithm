package programmingpearls.maxsubarray;

/**
 * Created by boyce on 16/6/2.
 */
public class LinearAlgorithm {
    public static void main(String[] args) {
        int[] array = {12,32,-21,-30,0,11,23,7,-3,36,-1,8,-17,8,5};
        o1(array);
        o3(array);
    }

    private static void o3(int[] array) {
        int s = 0, e = 0, max = 0;
        for (int i=0; i<array.length; i++) {
            for (int j=i; j<array.length; j++) {
                int sum = 0;
                for (int k=i; k<=j; k++) {
                    sum += array[k];
                }
                if (sum > max) {max = sum; s = i; e = j;}
            }
        }
        System.out.println("o3: \nmax=" + max + "\ns=" + s + "\ne=" + e + "\n");
    }

    private static void o1(int[] array) {
        int max = 0;
        int temp = 0;
        int s=0, e = 0;

        for (int i=0; i<array.length; i ++) {
            temp += array[i];
            if (temp > max) {
                max = temp;
                e = i;
            }
            if (temp < 0) {
                temp = 0;
                s = i+1;
            }
        }

        System.out.println("o1: \nmax=" + max + "\ns=" + s + "\ne=" + e + "\n");
    }
}
