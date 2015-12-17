package decisiontree;

import common.utils.AssertUtils;

import java.util.*;

/**
 * User: Boyce
 * Date: 15/12/15
 * Time: 22:59
 * 键值对对象
 */
public class Pair<N,V> {
    private N name;
    private V value;
    private Set<Example> examples;

    public Pair(N name, V value) {
        this.name = name;
        this.value = value;
        this.examples = new HashSet<Example>();
    }

    public void addExample(Example example) {
        AssertUtils.assertNotNull(example, "cannot add a null to pair's examples.");
        this.examples.add(example);
    }

    public int countExample() {
        return this.examples.size();
    }

    public N getName() {
        return name;
    }

    public V getValue() {
        return value;
    }

    public Set<Example> getExamples() {
        return examples;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;

        Pair pair = (Pair) o;

        if (!name.equals(pair.name)) return false;
        if (!value.equals(pair.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(name).append("=").append(value);
        return sb.toString();
    }

    /**
     * 训练数据属性
     */
    public static class Attribute extends Pair<String, Object> {
        public Attribute(String name, Object value) {
            super(name, value);
        }
    }

    /**
     * 训练数据所属类别
     */
    public static class Classification extends Pair<String, Object> {
        public Classification(Object value) {
            super("classification", value);
        }
    }
}
