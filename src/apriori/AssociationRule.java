package apriori;

import common.utils.AssertUtils;

/**
 * User: Boyce
 * Date: 11/12/15
 * Time: 15:07
 */
// 关联规则对象
public class AssociationRule {

    // 关联规则前件
    private Item frontItem;
    // 关联规则后建
    private Item afterItem;

    public AssociationRule(Item frontItem, Item afterItem) {
        AssertUtils.assertNotNull(frontItem, "");
        AssertUtils.assertNotNull(afterItem, "");

        this.frontItem = frontItem;
        this.afterItem = afterItem;
    }

    public Item getFrontItem() {
        return frontItem;
    }

    public Item getAfterItem() {
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
