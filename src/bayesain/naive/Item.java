package bayesain.naive;

import common.utils.AssertUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Boyce
 * Date: 6/12/15
 * Time: 17:13
 * To change this template use File | Settings | File Templates.
 */
public class Item {
    private static final double minRatio = 0.0001;

    // 保存训练数据集的item
    private final static Map<Object, Item> ITEMS = new HashMap<Object, Item>();

    // 保存每个item在各类别下的数量
    private Map<Classification, Integer> counter = new HashMap<Classification, Integer>();

    private Object value;
    private Item(Object value) {
        this.value = value;
    }

    public static synchronized Item getInstance(Object value) {
        Item item = ITEMS.get(value);
        if (item == null) {
            item = new Item(value);
            ITEMS.put(value, item);
        }
        return item;
    }

    public Object value() {
        return this.value;
    }

    // 保存该item在指定classification下的总数
    public void putCount(Classification classification, Integer count) {
        AssertUtils.assertNotNull(classification);
        AssertUtils.assertNotNull(count);
        AssertUtils.assertTrue(count > 0);

        this.counter.put(classification, count);
    }

    public int getCount(Classification classification) {
        Integer count = this.counter.get(classification);
        if (null == count) return 0;
        return count;
    }

    // 计算该item在指定classification中的比率
    // ratio = (1+item.count)/(classification.count+total.classification)
    public double ratio(Classification classification) {
        AssertUtils.assertNotNull(classification);

        int count = this.getCount(classification);
        int N = classification.getCount();
        double ratio = (double)(1+count)/(N + classification.getTotalClassification());
        if(ratio < minRatio)
            ratio = minRatio;
        return ratio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        if (!value.equals(item.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
