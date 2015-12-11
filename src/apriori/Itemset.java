package apriori;

import common.utils.AssertUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: Boyce
 * Date: 29/11/15
 * Time: 12:04
 */
public class Itemset implements Cloneable {
    protected List<Item> items;
    // TODO comment
    protected int cardinal_number;
    protected int length;

    // 保存一个Transactions 的引用
    protected Transactions transactions;

    private Itemset(int cardinal_number, Transactions transactions) {
        this.items = new ArrayList<Item>();
        this.length = 0;
        this.cardinal_number = cardinal_number;
        this.transactions = transactions;
    }

    public void addItem(Item item) {
        AssertUtils.assertNotNull(item, "cannot add a null item into the items.");
        AssertUtils.assertTrue(item.length() == this.cardinal_number, "the item length must be equals the itemset cardinal number.");

        this.items.add(item);
        this.length ++;
    }

    public int length() {
        return this.length;
    }

    /**
     * generate (k+1)-candidate itemset from k-frequent itemset
     * 注意： 注释中在不同的方法中说 “当前”的意义是不同的。
     * @return a (k+1)-candidate itemset
     */
    public Itemset frequent_gen(Transactions transactions) {
        int c_cardinal_number = this.cardinal_number + 1;
        Itemset frequentItemset = new Itemset(c_cardinal_number, transactions);

        MultiItem multiItem;
        for(int i=0; i<length; i++) {
            for (int j=i+1; j<length; j++) {
                Item i1 = items.get(i);
                Item i2 = items.get(j);

                multiItem = new MultiItem(this.cardinal_number + 1);
                if (this.cardinal_number == 1) {
                    SingleItem s1 = (SingleItem)i1;
                    SingleItem s2 = (SingleItem)i2;

                    // add item values
                    multiItem.addValue(s1.value());
                    multiItem.addValue(s2.value());

                    // TODO 注意 不是 this.combineTransaction(..)
                    frequentItemset.combineTransaction(i1, i2, multiItem);
                } else {

                    MultiItem m1 = (MultiItem)i1;
                    MultiItem m2 = (MultiItem)i2;

                    // m1 = [a,b,c] m2 = [a,b,d]
                    // multiItem = [a,b,c,d]
                    if (m1.sub_equals(m2)) {
                        // add item values
                        multiItem.addValues(m1.values());
                        multiItem.addValue(m2.values().get(m2.length() - 1));

                        // TODO 如果 multiItem 的所有(k-1) subset 中有任何subset不是频繁项集，则该 multiItem 不是频繁项集
                        // TODO 如果某个 subset 不属于当前项集(F(k-1)), 则该 subset 一定不是频繁项集

                        List<MultiItem> k_1Subset = multiItem.k_1Subset();
                        if (!this.items.containsAll(k_1Subset)) continue;

                        // TODO 注意 不是 this.combineTransaction(..)
                        frequentItemset.combineTransaction(i1, i2, multiItem);
                    }
                }
            }
        }

        // clear
        for (Item item: this.items) {
            item.clear();
        }
        return frequentItemset;
    }

    // 计算由 i1, i2 生成的项集 multiItem 是不是频繁项集
    // 如果是，将 multiItem 加入当前项 （“当前项" 是调用该方法的实例）
    private void combineTransaction(Item i1, Item i2, MultiItem multiItem) {
        // add item transactions
        Set<String> t1 = i1.ownerTransactionNames();
        Set<String> t2 = i2.ownerTransactionNames();

        // t1, t2 intersection
        // multiItem项集是由 i1和i2项集生成 F1(k-1)*F2(k-1),
        // 所以 multiItem 项集所属的transaction集合是i1, i2项集所在集合的交集
        Set<String> t = new HashSet<String>();
        t.addAll(t1);
        t.retainAll(t2);
        multiItem.addTransactions(t);

        this.addItemIfFrequent(multiItem);
    }

    // 计算item是不是一个当前项集的频繁项，如果是加入当前项
    private void addItemIfFrequent(Item item) {
        int count = item.count();
        int n = this.transactions.n();
        double sup = (double)count/n;
        boolean isFrequentItemset = sup >= this.transactions.minsup();

        if (isFrequentItemset) {
            this.addItem(item);

            System.out.println("> add    a item: " + item + ", sup=" + count + "/" + n + " = " + (double)count/n);
        } else  {
            System.out.println("> remove a item: " + item + ", sup=" + count + "/" + n + " = " + (double)count/n);
        }
    }

    public static Itemset init(Transactions transactions) {
        Itemset candidateItemset = new Itemset(1, transactions);
        List<SingleItem> singleItems = transactions.allSingleItems();
        for (Item item: singleItems) {
            candidateItemset.addItemIfFrequent(item);
        }
        return candidateItemset;
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Itemset{");
        sb.append("cardinal_number=").append(cardinal_number);
        sb.append(", items=").append(items);
        sb.append('}');
        return sb.toString();
    }

}
