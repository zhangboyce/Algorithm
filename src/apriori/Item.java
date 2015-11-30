package apriori;

import common.utils.AssertUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Boyce
 * Date: 30/11/15
 * Time: 17:12
 * To change this template use File | Settings | File Templates.
 */
public class Item {
    // the item contains transactions, transactions size is the item count.
    // TODO comment
    protected Set<Transaction> transactions;

    // item length, example: a :length=1, a,b: length=2
    protected int length;

    public Item(int length) {
        AssertUtils.assertTrue(length > 0, "item length must be greater than 0.");

        this.transactions = new HashSet<Transaction>();
        this.length = length;
    }

    public void addTransaction(Transaction transaction) {
        AssertUtils.assertNotNull(transaction, "cannot add a null transaction into the transactions.");
        this.transactions.add(transaction);
    }

    public void addTransactions(Set<Transaction> transactions) {
        for (Transaction transaction: transactions) {
            this.addTransaction(transaction);
        }
    }

    // TODO comment
    public int count() {
        return this.transactions.size();
    }

    public Set<Transaction> transactions() {
        return new HashSet<Transaction>(transactions);
    }

    public int length() {
        return this.length;
    }
}
