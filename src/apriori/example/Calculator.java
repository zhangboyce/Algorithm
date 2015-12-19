package apriori.example;

import apriori.Itemset;
import apriori.Transaction;
import apriori.Transactions;
import common.utils.FileUtils;
import common.utils.StringUtils;

import java.io.File;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Boyce
 * Date: 7/12/15
 * Time: 14:37
 * To change this template use File | Settings | File Templates.
 */
public class Calculator {
    public static void main(String[] args) {
        Transactions transactions = new Transactions(0.9, 0.8);

        List<String> lines = FileUtils.readLines(Calculator.class.getResourceAsStream("data/chess.dat"));

        long start = System.currentTimeMillis();
        Transaction t;
        for (int i=0;i<lines.size(); i++) {
            String line = lines.get(i);
            if (StringUtils.isNotEmpty(line)) {
                t = new Transaction("t" + i, transactions);

                String[] items = line.split(" ");
                int length = items.length;

                for (int j=0; j<length; j++) {
                    t.addItem(items[j]);
                }
            }
        }

        System.out.println(transactions);
        System.out.println(transactions.n());
        System.out.println("init take: " + (System.currentTimeMillis()-start) + "ms");
        start = System.currentTimeMillis();

        // for
        Itemset f = Itemset.init(transactions);
        System.out.println(f);

        while (!f.isEmpty()) {
            f = f.frequent_gen(transactions);
            System.out.println("frequent  set: " + f);
        }

        System.out.println("calculate take: " + (System.currentTimeMillis()-start) + "ms");

        System.out.println("rule: " + f.getParentItemset().rule_gen());

    }
}
