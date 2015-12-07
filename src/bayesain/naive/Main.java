package bayesain.naive;

/**
 * Created with IntelliJ IDEA.
 * User: Boyce
 * Date: 6/12/15
 * Time: 16:18
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) {
        Classifications cs = new Classifications();
        Classification c1 = new Classification("c1", 10000, cs);
        Classification c2 = new Classification("c2", 15000, cs);
        Classification c3 = new Classification("c3", 9000, cs);

        Item i1 = Item.getInstance("i1");
        i1.putCount(c1, 8000);
        i1.putCount(c2, 5000);
        i1.putCount(c3, 6000);

        Item i2 = Item.getInstance("i2");
        i2.putCount(c1, 200);
        i2.putCount(c2, 2000);
        i2.putCount(c3, 1000);

        Item i3 = Item.getInstance("i3");
        i3.putCount(c1, 2000);
        i3.putCount(c2, 8000);
        i3.putCount(c3, 3000);

        Item i4 = Item.getInstance("i4");
        i4.putCount(c1, 7000);
        i4.putCount(c2, 1000);
        i4.putCount(c3, 5000);

        Item i5 = Item.getInstance("i5");
        i5.putCount(c1, 9000);
        i5.putCount(c2, 3000);
        i5.putCount(c3, 7000);

        Item i6 = Item.getInstance("i6");
        i6.putCount(c1, 3000);
        i6.putCount(c2, 2000);
        i6.putCount(c3, 8000);

        XVector vector = new XVector(i2, i3, i5);
        Classification c = cs.classify(vector);

        System.out.println(c);
    }
}
