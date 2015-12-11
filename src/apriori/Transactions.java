package apriori;

import common.utils.AssertUtils;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Boyce
 * Date: 30/11/15
 * Time: 15:09
 * To change this template use File | Settings | File Templates.
 */
public class Transactions {
    private Set<Transaction> transactions;

    // save all single items
    private static final Map<Object, Item> allItems = new HashMap<Object, Item>();

    //minimum support
    private double minsup;

    //minimum confidence
    private double minconf;

    public Transactions(double minsup, double minconf) {
        this.transactions = new HashSet<Transaction>();
        this.minsup = minsup;
        this.minconf = minconf;
    }

    public int n() {
        return this.transactions.size();
    }

    public double minsup() {
        return this.minsup;
    }

    public double minconf() {
        return this.minconf;
    }

    public void addTransaction(Transaction transaction) {
        AssertUtils.assertNotNull(transaction);
        this.transactions.add(transaction);
    }

    public List<Transaction> transactions() {
        return new ArrayList<Transaction>(this.transactions);
    }

    public synchronized Item getItem(Object value) {
        AssertUtils.assertNotNull(value);

        Item item = allItems.get(value);
        if (null == item) {
            item = new Item(value);
            allItems.put(value, item);
        }
        return item;
    }

    public List<Item> allItems() {
        return new ArrayList<Item>(allItems.values());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transactions{");
        sb.append("transactions=").append(transactions);
        sb.append(", allItems=").append(allItems);
        sb.append(", minsup=").append(minsup);
        sb.append(", minconf=").append(minconf);
        sb.append('}');
        return sb.toString();
    }
}
