package decisiontree;

import common.utils.AssertUtils;

import java.util.*;

/**
 * User: Boyce
 * Date: 14/12/15
 * Time: 22:53
 * 训练样例集
 */
public class Examples {
    //[
    // "age": [[name:"age", value:"young"], [name:"age", value:"old"]],
    // "job": [[name:"job", value:"false"], [name:"job", value:"true"]]
    // ]
    // 保存全局的属性，每个属性（包括键值，详细参照Attribute的equals）全局只保存一个，且每个全局唯一属性保存自己所在的Example集合的引用。
    private Map<String, List<Pair.Attribute>> attributes = new HashMap<String, List<Pair.Attribute>>();
    // 保存全局的类别，每个类别全局只保存一个，且每个类别保存自己所在的Example集合的引用。
    private List<Pair.Classification> cfs = new ArrayList<Pair.Classification>();
    private List<Example> examples = new ArrayList<Example>();

    public Examples() {
    }

    // 根据name和value获取属性，如果不存在，则新建属性保存并返回
    public Pair.Attribute addAndGetAttribute(String name, Object value) {
        Pair.Attribute attribute = new Pair.Attribute(name, value);
        List<Pair.Attribute> list = attributes.get(name);
        if (list == null) {
            list = new ArrayList<Pair.Attribute>();
            attributes.put(name, list);
            list.add(attribute);
        } else {
            int index = list.indexOf(attribute);
            if (-1 != index) {
                attribute = list.get(index);
            } else {
                list.add(attribute);
            }
        }
        return attribute;
    }

    // 根据value获取类别，如果不存在，则新建类别保存并返回
    public Pair.Classification addAndGetClassification(Object value) {
        Pair.Classification cf = new Pair.Classification(value);
        int index = cfs.indexOf(cf);
        if (-1 != index) {
            cf = cfs.get(index);
        } else {
            cfs.add(cf);
        }
        return cf;
    }

    // 计算整个训练数据集的分类熵
    // entropy = -p(ci)*log2(p(ci)), i=1,2..n 分类个数
    public double entropy() {
        return this.entropy(this.examples);
    }

    public double entropy(List<Example> examples) {
        if (cfs.isEmpty() || cfs.size() == 1)
            return 0;

        double entropy = 0;
        int totalCount = examples.size();
        if (totalCount <= 1) return 0;

        for (Pair.Classification cf: cfs) {
            List<Example> newList = new ArrayList<Example>(examples);
            newList.retainAll(cf.getExamples());

            int cfCount = newList.size();
            double p = (double)cfCount/totalCount;
            if (p != 0) {
                entropy += p*(Math.log(p)/Math.log(2));
            }
        }
        return -entropy;
    }

    // 用指定 pairs 切分 指定 examples 的信息熵增益
    public double entropy(List<? extends Pair> pairs, List<Example> examples) {
        AssertUtils.assertNotEmpty(examples);

        double entropy = 0;
        int totalCount = examples.size();
        if (totalCount <= 1) return 0;

        for (Pair pair: pairs) {
            // 总的examples中 该pair包含有多少，求交集
            List<Example> pairExamples = pair.getExamples();
            List<Example> newList = new ArrayList<Example>(pairExamples);
            newList.retainAll(examples);

            int pairCount = newList.size();
            double p = (double)pairCount/totalCount;
            entropy += p * (-1)*this.entropy(newList);
        }
        return -entropy;
    }

    public void addExample(Example example) {
        examples.add(example);
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder("Examples{\n");
        for (Example example: examples) {
            sb.append("\t" + example + "\n");
        }
        sb.append('}');
        return sb.toString();
    }

    public List<Example> getExamples() {
        return new ArrayList<Example>(examples);
    }

    public Map<String, List<Pair.Attribute>> getAttributes() {
        return new HashMap<String, List<Pair.Attribute>>(attributes);
    }
}
