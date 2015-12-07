package apriori;

/**
 * Created with IntelliJ IDEA.
 * User: Boyce
 * Date: 30/11/15
 * Time: 22:22
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) {
        Transactions transactions = new Transactions(0.25, 0.4);

        Transaction t1 = new Transaction("t1", transactions);
        t1.addItem("a");
        t1.addItem("b");
        t1.addItem("c");

        Transaction t2 = new Transaction("t2", transactions);
        t2.addItem("a");
        t2.addItem("d");

        Transaction t3 = new Transaction("t3", transactions);
        t3.addItem("d");
        t3.addItem("e");

        Transaction t4 = new Transaction("t4", transactions);
        t4.addItem("a");
        t4.addItem("b");
        t4.addItem("d");

        Transaction t5 = new Transaction("t5", transactions);
        t5.addItem("a");
        t5.addItem("b");
        t5.addItem("c");
        t5.addItem("d");
        t5.addItem("f");

        Transaction t6 = new Transaction("t6", transactions);
        t6.addItem("b");
        t6.addItem("c");
        t6.addItem("f");

        Transaction t7 = new Transaction("t7", transactions);
        t7.addItem("b");
        t7.addItem("f");
        t7.addItem("c");

        System.out.println(transactions);
        System.out.println(transactions.n());

        Itemset f1 = Itemset.init(transactions);
        System.out.println(f1);

        Itemset f2 = f1.frequent_gen(transactions);
        System.out.println(f2);

        Itemset f3 = f2.frequent_gen(transactions);
        System.out.println(f3);

        System.out.println("-----------------------------------------------");

        // for
        Itemset f = Itemset.init(transactions);
        System.out.println(f);

        while (!f.isEmpty()) {
            f = f.frequent_gen(transactions);
            System.out.println(f);
        }
    }
}
