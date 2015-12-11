package apriori;

import common.utils.AssertUtils;

import java.util.*;

/**
 * User: Boyce
 * Date: 29/11/15
 * Time: 12:04
 */
public class Itemset implements Cloneable {
    protected List<Item> items;

    // 生成该k项集的(k-1)项集
    protected Itemset parentItemset;

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
        frequentItemset.parentItemset = this;

        for(int i=0; i<length; i++) {
            for (int j=i+1; j<length; j++) {
                Item i1 = items.get(i);
                Item i2 = items.get(j);

                if (i1.sub_equals(i2)) {
                    Item item = Item.merge(i1, i2);

                    // TODO 如果 multiItem 的所有(k-1) subset 中有任何subset不是频繁项集，则该 multiItem 不是频繁项集
                    // TODO 如果某个 subset 不属于当前项集(F(k-1)), 则该 subset 一定不是频繁项集

                    List<Item> k_1Subset = item.k_1Subset();
                    if (!this.items.containsAll(k_1Subset)) continue;

                    // TODO 注意 不是 this.combineTransaction(..)
                    frequentItemset.combineTransaction(i1, i2, item);
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
    private void combineTransaction(Item i1, Item i2, Item item) {
        // add item transactions
        Set<String> t1 = i1.ownerTransactionNames();
        Set<String> t2 = i2.ownerTransactionNames();

        // t1, t2 intersection
        // multiItem项集是由 i1和i2项集生成 F1(k-1)*F2(k-1),
        // 所以 multiItem 项集所属的transaction集合是i1, i2项集所在集合的交集
        Set<String> t = new HashSet<String>();
        t.addAll(t1);
        t.retainAll(t2);
        item.addTransactions(t);

        this.addItemIfFrequent(item);
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
        Itemset frequentItemset = new Itemset(1, transactions);
        List<Item> singleItems = transactions.allItems();
        for (Item item: singleItems) {
            frequentItemset.addItemIfFrequent(item);
        }
        return frequentItemset;
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    // 该频繁项集生成关联规则
    public List<AssociationRule> rule_gen() {

        if (1 == this.cardinal_number)
            return Collections.EMPTY_LIST;

        List<AssociationRule> f_rules = new ArrayList<AssociationRule>();
        List<Item> items = this.items;

        // 计算频繁项集中每个频繁项的关联规则
        for (int i=0; i<items.size(); i++) {
            Item item = items.get(i);

            // TODO 如果一条关联规则的后件为a，那么所有以a的非空子集作为后件的候选规则
            // TODO 都是关联规则，换句话说，如果以a为后件的候选规则不是关联规则，则
            // TODO 任何以a为子项的父集作为后件的候选规则都不是关联规则。

            // 获取该频繁项的所有 1-候选规则
            List<AssociationRule> c1_rules = item.a_1AssociationRules();
            this.recurse(f_rules, c1_rules, item);
        }
        return f_rules;
    }

    private void recurse(List<AssociationRule> f_rules, List<AssociationRule> c_rules, Item item) {
        for (AssociationRule rule: c_rules) {
            if (isFrequentRule(rule, item)) {
                f_rules.add(rule);

                List<AssociationRule> ck_rules = item.a_kAssociationRules(rule.getAfterItem());
                this.recurse(f_rules, ck_rules, item);
            }
        }
    }

    private boolean isFrequentRule(AssociationRule rule, Item item) {
        Item frontItem = rule.getFrontItem();

        int n = frontItem.length();
        int n_parent = this.cardinal_number - n;

        Itemset n_parent_itemset = this;
        for (int i=0; i<n_parent; i++)
            n_parent_itemset = n_parent_itemset.parentItemset;

        int front_count = n_parent_itemset.count(frontItem);

        double conf = (double)item.count()/front_count;
        boolean isFrequentRule = conf >= this.transactions.minconf();

        if (isFrequentRule) {
            System.out.println("> add    a rule: " + rule + ", conf=" + item.count() + "/" + front_count + " = " + (double)item.count()/front_count);
        }
        return isFrequentRule;
    }

    public int count(Item item) {
        int count = 0;
        for (Item it: this.items) {
            if (it.equals(item)) {
                count = it.count();
                break;
            }
        }
        return count;
    }

    public Itemset getParentItemset() {
        return parentItemset;
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
