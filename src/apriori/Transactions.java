package apriori;

import common.utils.AssertUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

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
    private static final Map<Object, SingleItem> allSingleItems = new HashMap<Object, SingleItem>();

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

    public synchronized SingleItem getItem(Object value) {
        AssertUtils.assertNotNull(value);

        SingleItem item = allSingleItems.get(value);
        if (null == item) {
            item = new SingleItem(value);
            allSingleItems.put(value, item);
        }
        return item;
    }

    public List<SingleItem> allSingleItems() {
        return new ArrayList<SingleItem>(allSingleItems.values());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transactions{");
        sb.append("transactions=").append(transactions);
        sb.append(", allSingleItems=").append(allSingleItems);
        sb.append(", minsup=").append(minsup);
        sb.append(", minconf=").append(minconf);
        sb.append('}');
        return sb.toString();
    }
}
