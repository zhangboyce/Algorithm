package apriori;

import common.utils.AssertUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Boyce
 * Date: 29/11/15
 * Time: 12:02
 * To change this template use File | Settings | File Templates.
 */
public class Transaction {
    private List<SingleItem> singleItems;
    private String name;
    private Transactions transactions;

    public Transaction(String name, Transactions transactions) {
        this.name = name;
        this.singleItems = new ArrayList<SingleItem>();
        this.transactions = transactions;

        // TODO comment
        this.transactions.addTransaction(this);
    }

    // TODO comment
    public void addItem(Object value) {
        AssertUtils.assertNotNull(value, "cannot add a item value into the transaction.");

        SingleItem singleItem = this.transactions.getItem(value);
        if (!this.singleItems.contains(singleItem)) {
            this.singleItems.add(singleItem);
            singleItem.addTransaction(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;

        Transaction that = (Transaction) o;

        if (name != that.name) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transaction{");
        sb.append("name='").append(name).append('\'');
        sb.append(", singleItems=").append(singleItems);
        sb.append('}');
        return sb.toString();
    }
}
