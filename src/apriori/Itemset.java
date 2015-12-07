package apriori;

import common.utils.AssertUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Boyce
 * Date: 29/11/15
 * Time: 12:04
 * To change this template use File | Settings | File Templates.
 */
public class Itemset implements Cloneable {
    protected List<Item> items;

    // TODO comment
    protected int cardinal_number;

    protected int length;

    private Itemset(int cardinal_number) {
        this.items = new ArrayList<Item>();
        this.length = 0;
        this.cardinal_number = cardinal_number;
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

    public List<Item> values() {
        List<Item> list = new ArrayList<Item>(this.items);
        return list;
    }

    /**
     * generate (k+1)-candidate itemset from k-frequent itemset
     * @return a (k+1)-candidate itemset
     */
    public Itemset candidate_gen() {
        int c_cardinal_number = this.cardinal_number + 1;
        Itemset candidateItemset = new Itemset(c_cardinal_number);

        for(int i=0; i<length; i++) {
            for (int j=i+1; j<length; j++) {
                Item i1 = items.get(i);
                Item i2 = items.get(j);

                MultiItem multiItem = new MultiItem(this.cardinal_number + 1);
                if (this.cardinal_number == 1) {
                    SingleItem s1 = (SingleItem)i1;
                    SingleItem s2 = (SingleItem)i2;

                    // add item values
                    multiItem.addValue(s1.value());
                    multiItem.addValue(s2.value());

                    this.combineTransaction(i1, i2, multiItem, candidateItemset);
                } else {

                    MultiItem m1 = (MultiItem)i1;
                    MultiItem m2 = (MultiItem)i2;

                    // TODO comment
                    if (m1.sub_equals(m2)) {
                        // add item values
                        multiItem.addValues(m1.values());
                        multiItem.addValue(m2.values().get(m2.length() - 1));

                        this.combineTransaction(i1, i2, multiItem, candidateItemset);
                    }
                }
            }
        }
        return candidateItemset;
    }

    private void combineTransaction(Item i1, Item i2, MultiItem multiItem, Itemset candidateItemset) {
        // add item transactions
        Set<Transaction> t1 = i1.transactions();
        Set<Transaction> t2 = i2.transactions();

        // t1, t2 intersection
        // TODO comment
        Set<Transaction> t = new HashSet<Transaction>();
        t.addAll(t1);
        t.retainAll(t2);

        multiItem.addTransactions(t);
        candidateItemset.addItem(multiItem);
    }

    public Itemset frequent_gen(Transactions transactions) {
        Itemset frequentItemset = new Itemset(this.cardinal_number);
        for (Item item : items) {
            int count = item.count();
            int n = transactions.n();

            double sup = (double)count/n;
            if (sup >= transactions.minsup()) {
                frequentItemset.addItem(item);

                System.out.println("> add    a item: " + item + ", sup=" + count + "/" + n + " = " + (double)count/n);
            } else  {
                System.out.println("> remove a item: " + item + ", sup=" + count + "/" + n + " = " + (double)count/n);
            }
        }
        System.out.println();

        return frequentItemset;
    }

    public static Itemset init(Transactions transactions) {
        Itemset candidateItemset = new Itemset(1);
        List<SingleItem> singleItems = transactions.allSingleItems();
        for (Item item: singleItems) {
            candidateItemset.addItem(item);
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
