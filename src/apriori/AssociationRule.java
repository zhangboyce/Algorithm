package apriori;

import common.utils.AssertUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: Boyce
 * Date: 11/12/15
 * Time: 15:07
 */
// 关联规则对象
public class AssociationRule {

    // 关联规则前件
    private List<SingleItem> frontItem;
    // 关联规则后建
    private List<SingleItem> afterItem;

    public AssociationRule(List<SingleItem> frontItem, List<SingleItem> afterItem) {
        AssertUtils.assertNotEmpty(frontItem);
        AssertUtils.assertNotEmpty(afterItem);

        this.frontItem = new ArrayList<SingleItem>(frontItem);
        this.afterItem = new ArrayList<SingleItem>(afterItem);
    }

    public AssociationRule(SingleItem frontItem, List<SingleItem> afterItem) {
        this(asList(frontItem), afterItem);
    }

    public AssociationRule(List<SingleItem> frontItem, SingleItem afterItem) {
        this(frontItem, asList(afterItem));
    }

    public AssociationRule(SingleItem frontItem, SingleItem afterItem) {
        this(asList(frontItem), asList(afterItem));
    }

    private static List<SingleItem> asList(SingleItem singleItem) {
        List<SingleItem> list = new ArrayList<SingleItem>();
        list.add(singleItem);
        return list;
    }

    public List<SingleItem> getFrontItem() {
        return frontItem;
    }

    public List<SingleItem> getAfterItem() {
        return afterItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AssociationRule)) return false;

        AssociationRule that = (AssociationRule) o;

        if (!afterItem.equals(that.afterItem)) return false;
        if (!frontItem.equals(that.frontItem)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = frontItem.hashCode();
        result = 31 * result + afterItem.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AssociationRule{");
        sb.append(frontItem);
        sb.append(" > ");
        sb.append(afterItem);
        sb.append('}');
        return sb.toString();
    }
}
