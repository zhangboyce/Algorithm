package bayesain.naive;

import common.utils.AssertUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Boyce
 * Date: 6/12/15
 * Time: 16:15
 * To change this template use File | Settings | File Templates.
 */
public class Classification {
    private int count;
    private Classifications classifications;
    private String name;

    public Classification(String name,  int count, Classifications classifications) {
        this.name = name;
        // 所有训练数据中，该类别所占的条数
        this.count = count;
        this.setClassifications(classifications);
        this.classifications.addClassification(this);
    }

    // 该类别在总的类别中出现的概率
    public double p_Classification() {
        return (double)count/this.classifications.getTotalCount();
    }

    public int getCount() {
        return count;
    }

    // 返回分类的个数
    public int getTotalClassification() {
        return this.classifications.getClassificationSize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Classification)) return false;

        Classification that = (Classification) o;

        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public void setClassifications(Classifications classifications) {
        this.classifications = classifications;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Classification{");
        sb.append("name='").append(name).append('\'');
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }
}
