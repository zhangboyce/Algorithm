package decisiontree;

import java.util.ArrayList;
import java.util.List;
import common.utils.AssertUtils;

/**
 * User: Boyce
 * Date: 14/12/15
 * Time: 23:26
 * 一条训练样例
 */
public class Example {
    private String id;
    private Pair.Classification cf;
    private List<Pair.Attribute> attributes;

    public Example(String id, Object cf) {
        AssertUtils.assertNotNull(cf, "cannot construct a example with null classification.");
        this.attributes = new ArrayList<Pair.Attribute>();
        this.id = id;

        // 根据Example的cf值获取所属类别实例并让this.cf的引用指向该实例
        this.cf = Examples.addAndGetClassification(cf);
        this.cf.addExample(this);
    }

    public void addAttribute(String name, Object value) {
        Pair.Attribute attribute = Examples.addAndGetAttribute(name, value);
        this.attributes.add(attribute);
        attribute.addExample(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Example)) return false;

        Example example = (Example) o;

        if (id != example.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public Pair.Classification getCf() {
        return cf;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Example{");
        sb.append(attributes +", ");
        sb.append(cf);
        sb.append(", id='").append(id).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
