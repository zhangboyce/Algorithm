package apriori;

import common.utils.AssertUtils;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Boyce
 * Date: 29/11/15
 * Time: 12:03
 * To change this template use File | Settings | File Templates.
 */
public class MultiItem extends Item {

    // item values
    protected List<SingleItem> values;

    public MultiItem(int length) {
        super(length);

        AssertUtils.assertTrue(length > 1, "MultiItem's length must greater than 1.");
        this.values = new ArrayList<SingleItem>(length);
    }

    public MultiItem(List<SingleItem> items) {
        this(items.size());
        this.values.addAll(items);
    }

    public void addItem(SingleItem singleItem) {
        AssertUtils.assertNotNull(singleItem, "cannot add a null item into item.");
        AssertUtils.assertTrue(this.values.size() < this.length, "out of the item length.");

        // TODO 保证list里面的元素不重复
        if (!this.values.contains(singleItem))
            this.values.add(singleItem);
    }

    public void addItems(List<SingleItem> singleItems) {
        for (SingleItem item: singleItems)
            this.addItem(item);
    }

    public void addValue(Object obj) {
        AssertUtils.assertNotNull(obj, "cannot add a null value into item.");
        SingleItem singleItem = new SingleItem(obj);

        this.addItem(singleItem);
    }

    public void addValues(List<Object> objs) {
        AssertUtils.assertNotEmpty(objs, "cannot add a empty list into item.");
        for(Object obj: objs) {
            this.addValue(obj);
        }
    }

    public List<Object> values() {
        return new ArrayList<Object>(values);
    }

    /**
     * sub equals, compare 0..length-2
     * "abc".sub_equals("abd") == true
     * "abc".sub_equals("abc") == true
     * "abc".sub_equals("dbc") == false
     * "abc".sub_equals("acb") == false
     * @param multiItem
     * @return
     */
    public boolean sub_equals(MultiItem multiItem) {
        AssertUtils.assertNotEmpty(this.values);
        AssertUtils.assertNotNull(multiItem);
        AssertUtils.assertTrue(multiItem.length() == this.length, "cannot sub_equals two items that have different length.");

        boolean sub_equals = true;
        for (int i=0; i<length-1; i++) {
            Object obj1 = this.values.get(i);
            Object obj2 = multiItem.values.get(i);

            if (!obj1.equals(obj2)) {
                sub_equals = false;
                break;
            }
        }

        return sub_equals;
    }

    // this = [a,b,c,d]
    // k_1Subset = [[a,b,c],[a,b,d],[a,c,d],[b,c,d]]
    public List<MultiItem> k_1Subset() {
        int n = this.values.size();
        if (n <= 2) return Collections.EMPTY_LIST;

        List<MultiItem> subset = new ArrayList<MultiItem>(n);
        MultiItem multiItem;
        List<SingleItem> subSingleItems;
        for (int i=0; i<n; i++) {
            subSingleItems = new ArrayList<SingleItem>(this.values);
            subSingleItems.remove(i);
            multiItem = new MultiItem(subSingleItems);
            subset.add(multiItem);
        }

        return subset;
    }

    // 称以c个单值作为后件的候选规则为 c-候选规则，该方法生成该MultiItem的所有的 1-候选规则
    public List<AssociationRule> a_1AssociationRules() {
        List<AssociationRule> rules = new ArrayList<AssociationRule>(this.values.size());
        for (int i=0; i<this.values.size(); i++) {
            SingleItem afterItem = this.values.get(i);

            List<SingleItem> frontItem = new ArrayList<SingleItem>(this.length-1);
            for (int j=0; j<this.values.size(); j++) {
                if (j != i) {
                    frontItem.add(this.values.get(j));
                }
            }
            AssociationRule rule = new AssociationRule(frontItem, afterItem);
            rules.add(rule);
        }
        return rules;
    }

    // 该方法生成后件包含 items 的 (items.size()+1)-候选规则
    public List<AssociationRule> a_kAssociationRules(List<SingleItem> items) {
        if (null == items || items.isEmpty() || items.size() >= this.values.size()
                || !this.values.containsAll(items))
            return Collections.EMPTY_LIST;

        List<SingleItem> otherItems = new ArrayList<SingleItem>(this.values);
        otherItems.removeAll(items);

        List<AssociationRule> rules = new ArrayList<AssociationRule>(this.values.size()-items.size());
        for (int i=0; i<otherItems.size(); i++) {

            List<SingleItem> afterItem = new ArrayList<SingleItem>(items.size()+1);
            afterItem.addAll(items);
            afterItem.add(otherItems.get(i));

            List<SingleItem> frontItem = new ArrayList(otherItems.size()-1);
            for (int j=0; j<otherItems.size(); j++) {
                if (j != i) {
                    frontItem.add(otherItems.get(j));
                }
            }
            AssociationRule rule = new AssociationRule(frontItem, afterItem);
            rules.add(rule);
        }

        return rules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MultiItem)) return false;

        MultiItem multiItem = (MultiItem) o;

        if (this.length != multiItem.length) {
            return false;
        }

        // TODO 因为两个list里面的元素都不重复，可以用这种方式，如果list里面的元素有重复，不可以。
        if (!values.containsAll(multiItem.values) &&
                multiItem.values.containsAll(values)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        for (SingleItem obj: values) {
            result = PRIME * result + obj.hashCode();
        }
        return result;
    }

    @Override
    public String toString() {
        return values.toString();
    }

    public static void main(String[] args) {
        MultiItem i1 = new MultiItem(1);
        MultiItem i2 = new MultiItem(1);

        i1.addValue("a");

        i2.addValue("a");

        System.out.println(i1.sub_equals(i2));
    }
}
