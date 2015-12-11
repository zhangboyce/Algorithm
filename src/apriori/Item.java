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
    // 每个item保存其所在的transaction 的名字集合
    protected Set<String> ownerTransactionNames;
    protected int count;

    // item length, example: a :length=1, a,b: length=2
    protected int length;

    public Item(int length) {
        AssertUtils.assertTrue(length > 0, "item length must be greater than 0.");

        this.ownerTransactionNames = new HashSet<String>();
        this.length = length;
    }

    public void addTransaction(String transactionName) {
        AssertUtils.assertNotEmpty(transactionName, "cannot add a empty transaction name into the transactions.");
        this.ownerTransactionNames.add(transactionName);
    }

    public void addTransactions(Set<String> transactionNames) {
        for (String name: transactionNames) {
            this.addTransaction(name);
        }
    }

    //  count(X) = |{t(i)|X属于t(i), t(i)属于ts}|
    // item 的count 是 item所属于的t的数量
    public int count() {
        if (this.ownerTransactionNames == null ||
                this.ownerTransactionNames.isEmpty())
            return count;

        return this.ownerTransactionNames.size();
    }

    public Set<String> ownerTransactionNames() {
        return new HashSet<String>(ownerTransactionNames);
    }

    public int length() {
        return this.length;
    }

    public void clear() {
        // 保存下count
        this.count = this.ownerTransactionNames.size();
        this.ownerTransactionNames.clear();
    }
}
