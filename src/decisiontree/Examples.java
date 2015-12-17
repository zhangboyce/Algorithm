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
    private final static Map<String, List<Pair.Attribute>> attributes = new HashMap<String, List<Pair.Attribute>>();
    // 保存全局的类别，每个类别全局只保存一个，且每个类别保存自己所在的Example集合的引用。
    private final static List<Pair.Classification> cfs = new ArrayList<Pair.Classification>();

    private final static List<Example> examples = new ArrayList<Example>();
    private Examples() {
    }

    // 根据name和value获取属性，如果不存在，则新建属性保存并返回
    public static Pair.Attribute addAndGetAttribute(String name, Object value) {
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
    public static Pair.Classification addAndGetClassification(Object value) {
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
    public static double entropy() {
        if (cfs.isEmpty() || cfs.size() == 1)
            return 0;

        return entropy(cfs, examples);
    }

    // 用指定 pairs 切分 指定 examples 的信息熵
    private static double entropy(List<? extends Pair> pairs, List<Example> examples) {
        AssertUtils.assertNotEmpty(pairs);
        AssertUtils.assertNotEmpty(examples);

        double entropy = 0;
        int totalCount = examples.size();
        if (totalCount <= 1) return 0;

        for (Pair pair: pairs) {
            // 总的examples中 该pair包含有多少，求交集
            Set<Example> pairExamples = pair.getExamples();
            List<Example> newList = new ArrayList<Example>(pairExamples);
            newList.retainAll(examples);

            int pairCount = newList.size();
            double p = (double)pairCount/totalCount;
            entropy += p*(Math.log(p)/Math.log(2));
        }
        return -entropy;
    }

    public static void addExample(Example example) {
        examples.add(example);
    }

    public static String asString() {
        final StringBuilder sb = new StringBuilder("Examples{\n");
        for (Example example: examples) {
            sb.append("\t" + example + "\n");
        }
        sb.append('}');
        return sb.toString();
    }
}
