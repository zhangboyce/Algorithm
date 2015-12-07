package bayesain.naive;

import common.utils.AssertUtils;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Boyce
 * Date: 6/12/15
 * Time: 14:34
 * To change this template use File | Settings | File Templates.
 */

// 待分类向量
public class XVector {

    private static final int ENHANCEMENT_FACTOR = 1000000;

    protected List<Item> items;

    public XVector() {
        this.items = new ArrayList<Item>();
    }

    public XVector(Item item, Item... items) {
        this();
        this.items.add(item);
        this.items.addAll(Arrays.asList(items));
    }

    public XVector(List<Item> items) {
        this();
        this.items.addAll(items);
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    // 计算在指定类别下该向量出现的概率
    public double p_XVector_Classification(Classification classification) {
        AssertUtils.assertNotNull(classification, "classification cannot be null.");

        // P(XVector|Classification) 其中XVector = {item1,item2...itemn}
        // 约等于 P(item1|Classification)*P(item2|Classification)*...*P(itemn|Classification)
        // 假设 XVector 中item之间相互两两独立
        double probability = 1 * ENHANCEMENT_FACTOR;
        for (Item item: this.items) {
            probability *= item.ratio(classification);
        }
        return probability;
    }
}
