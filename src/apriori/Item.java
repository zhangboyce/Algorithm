package apriori;

import java.util.*;

import common.utils.AssertUtils;

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
    private Set<String> ownerTransactionNames;
    private int count;

    // Item 项的最后一个值
    private Object value;

    // Item 项的前n-1个值，
    private List<Object> values;

    public Item(Object value) {
        AssertUtils.assertNotNull(value, "cannot new a item with null value.");

        this.value = value;
        this.ownerTransactionNames = new HashSet<String>();
        this.values = new ArrayList<Object>();
    }

    public Item(List<Object> values) {
        AssertUtils.assertNotEmpty(values, "cannot new a item with empty values.");

        List<Object> allValues = new ArrayList<Object>(values);
        this.value = allValues.get(allValues.size()-1);
        allValues.remove(allValues.size()-1);
        this.values = allValues;

        this.ownerTransactionNames = new HashSet<String>();
    }

    public static Item merge(Item i1, Item i2) {
        AssertUtils.assertNotNull(i1, "cannot merge a null item to another.");
        AssertUtils.assertNotNull(i2, "cannot merge a null item to another.");

        Item item = new Item(i1.allValues());
        item.addValue(i2.getValue());
        return item;
    }

    /**
     * sub equals, compare 0..length-2
     * "abc".sub_equals("abd") == true
     * "abc".sub_equals("abc") == true
     * "abc".sub_equals("dbc") == false
     * "abc".sub_equals("acb") == false
     * @param item
     * @return
     */
    public boolean sub_equals(Item item) {
        AssertUtils.assertNotNull(item);
        AssertUtils.assertTrue(item.length() == this.length(), "cannot sub_equals two items that have different length.");

        // TODO 因为两个list里面的元素都不重复，可以用这种方式，如果list里面的元素有重复，不可以。
        return item.values.isEmpty() ||
                (item.values.containsAll(this.values) &&
                this.values.containsAll(item.values));
    }

    // this = [a,b,c,d]
    // k_1Subset = [[a,b,c],[a,b,d],[a,c,d],[b,c,d]]
    public List<Item> k_1Subset() {
        if (this.length() <= 2) return Collections.EMPTY_LIST;

        List<Item> subset = new ArrayList<Item>(this.length());
        Item item;
        for (int i=0; i<this.length(); i++) {
            List<Object> allValues = this.allValues();
            allValues.remove(i);

            item = new Item(allValues);
            subset.add(item);
        }
        return subset;
    }

    // 称以c个单值作为后件的候选规则为 c-候选规则，该方法生成该 Item 的所有的 1-候选规则
    public List<AssociationRule> a_1AssociationRules() {
        List<Object> allValues = this.allValues();
        List<AssociationRule> rules = new ArrayList<AssociationRule>(this.length());
        for (int i=0; i<this.length(); i++) {
            Object afterValue = allValues.get(i);

            List<Object> frontValues = new ArrayList<Object>(this.length()-1);
            for (int j=0; j<allValues.size(); j++) {
                if (j != i) {
                    frontValues.add(allValues.get(j));
                }
            }
            AssociationRule rule = new AssociationRule(new Item(frontValues), new Item(afterValue));
            rules.add(rule);
        }
        return rules;
    }

    // 该方法生成后件包含 item 的 (items.length()+1)-候选规则
    public List<AssociationRule> a_kAssociationRules(Item item) {
        if (null == item || item.isEmpty() || item.length() >= this.length()-1
                || !this.allValues().containsAll(item.allValues()))
            return Collections.EMPTY_LIST;

        List<Object> otherObjs = new ArrayList<Object>(this.allValues());
        otherObjs.removeAll(item.allValues());

        List<AssociationRule> rules = new ArrayList<AssociationRule>(this.length()-item.length());
        for (int i=0; i<otherObjs.size(); i++) {

            List<Object> afterObjs = new ArrayList<Object>(item.length()+1);
            afterObjs.addAll(item.allValues());
            afterObjs.add(otherObjs.get(i));

            List<Object> frontObjs = new ArrayList(otherObjs.size()-1);
            for (int j=0; j<otherObjs.size(); j++) {
                if (j != i) {
                    frontObjs.add(otherObjs.get(j));
                }
            }
            AssociationRule rule = new AssociationRule(new Item(frontObjs), new Item(afterObjs));
            rules.add(rule);
        }

        return rules;
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
        return this.values.size()+1;
    }

    public void clear() {
        // 保存下count
        this.count = this.ownerTransactionNames.size();
        this.ownerTransactionNames.clear();
    }

    public List<Object> allValues() {
        List<Object> list = new ArrayList<Object>(this.values);
        list.add(value);
        return list;
    }

    public boolean isEmpty() {
        return this.length() == 0;
    }

    public void addValue(Object value) {
        AssertUtils.assertNotNull(value, "cannot add a null value to the item.");
        this.values.add(this.value);
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        if (this.length() != item.length()) {
            return false;
        }

        if (!sub_equals(item)) {
            return false;
        }

        if (!value.equals(item.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        for (Object obj: values) {
            result = PRIME * result + obj.hashCode();
        }
        result = PRIME * result + value.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return this.allValues().toString();
    }

    public Object getValue() {
        return value;
    }
}
