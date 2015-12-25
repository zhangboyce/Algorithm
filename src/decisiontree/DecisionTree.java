package decisiontree;

import common.utils.AssertUtils;
import common.utils.CollectionUtils;

import java.util.*;

/**
 * User: Boyce
 * Date: 17/12/15
 * Time: 17:55
 */
public class DecisionTree {
    private Node root;
    private Examples examples;

    public DecisionTree(Examples examples) {
        this.examples = examples;
    }

    public void build() {
        this.root = this.build(new ArrayList<Pair.Attribute>(), this.examples.getExamples());
    }

    private Node build(List<Pair.Attribute> superAttributes, List<Example> exampleList) {
        // 计算即将用来划分训练集的属性集，总的属性集-已经被父节点用来划分过得属性集
        Map<String, List<Pair.Attribute>> attributeMap = this.examples.getAttributes();
        if (CollectionUtils.isNotEmpty(superAttributes)) {
            for (Pair.Attribute superAttribute: superAttributes) {
                attributeMap.remove(superAttribute.getName());
            }
        }

        // TODO if attributeMap isEmpty
        if (attributeMap.isEmpty() || exampleList.isEmpty()) return null;

        // 计算具有最大熵增益的划分的属性。
        String maxGainAttributeName = this.getMaxGainAttributeName(attributeMap, exampleList);
        List<Pair.Attribute> maxGainAttributes = attributeMap.get(maxGainAttributeName);

        //用该属性名创建节点，用该属性名的所有属性值创建该节点的分支。
        Node node = new Node(maxGainAttributeName);
        for (Pair.Attribute attribute: maxGainAttributes) {
            // 该训练集中，指定属性值所包含的训练集
            List<Example> subExamples = new ArrayList<Example>(attribute.getExamples());
            subExamples.retainAll(exampleList);

            Object value = attribute.getValue();
            Node.Branch branch = new Node.Branch();
            branch.value = value;
            branch.count = subExamples.size();

            // 指定属性值所包含的训练集总共有几个分类，如果不止一个分类，则用该属性值所包含的训练集继续递归创建该分支的子节点
            Set<Pair.Classification> cfs = getCfs(subExamples);
            branch.cfs = cfs;
            if (cfs.size() != 1) {
                superAttributes.add(attribute);
                Node child = this.build(superAttributes, subExamples);
                if (null != child)
                    branch.node = child;
            }
            node.branches.add(branch);
        }
        node.count = exampleList.size();
        return node;
    }

    // 使用指定的属性集划分指定的训练集，返回信息熵增益最大的属性作为划分。
    // 信息增益优先选择有较多可能取值的属性。
    private String getMaxGainAttributeName(Map<String, List<Pair.Attribute>> attributeMap, List<Example> exampleList) {
        // 计算指定训练集的分类信息熵
        double entropy = this.examples.entropy(exampleList);

        double maxGain = 0;
        String maxGainAttributeName = null;
        for (String key: attributeMap.keySet()) {
            List<Pair.Attribute> attributes = attributeMap.get(key);

            // 计算用该属性划分训练集的信息熵以及划分后熵的增益。
            double entropyAi = this.examples.entropy(attributes, exampleList);
            double entropyGain = entropy - entropyAi;
            if (entropyGain >= maxGain) {
                maxGain = entropyGain;
                maxGainAttributeName = key;
            }
            System.out.println("gain(D,"+key+") = " + entropy + "-" + entropyAi +" = " + (entropyGain));
        }
        System.out.println("maxGainAttributeName: " + maxGainAttributeName);

        // TODO 如果maxGain增益小于一个设定的阀值，算法应该在该分支上停止，并选取子集中最频繁的类别作为
        // TODO 该分支的类别

        return maxGainAttributeName;
    }

    private static class Node {
        private String name;
        private Set<Branch> branches;
        private int count;

        private Node(String name) {
            this.name = name;
            this.branches = new HashSet<Branch>();
        }

        public boolean isLeaf() {
            boolean isLeaf = true;
            for (Branch branch: branches) {
                if (!branch.isLeaf()) {
                    isLeaf = false;
                    break;
                }
            }
            return isLeaf;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Node{");
            sb.append("name='").append(name).append('\'');
//            sb.append(", branches=").append(branches);
            sb.append(", count=").append(count);
            sb.append('}');
            return sb.toString();
        }

        private static class Branch {
            private Set<Pair.Classification> cfs;
            private Node node;
            private Object value;
            private int count;

            public boolean isLeaf() {
                return null == node;
            }

            @Override
            public String toString() {
                final StringBuilder sb = new StringBuilder("Branch{");
                sb.append("value=").append(value);
                sb.append(", cfs=").append(cfs);
//                sb.append(", node=").append(node);
                sb.append(", count=").append(count);
                sb.append('}');
                return sb.toString();
            }
        }
    }

    private static Set<Pair.Classification> getCfs(List<Example> exampleList) {
        if (CollectionUtils.isEmpty(exampleList))
            return Collections.EMPTY_SET;

        Set<Pair.Classification> cfs = new HashSet<Pair.Classification>();
        for (Example example: exampleList) {
            cfs.add(example.getCf());
        }

        return cfs;
    }

    @Override
    public String toString() {
        List<String> pathList = new ArrayList<String>();
        Stack<Object> stack = new Stack<Object>();
        this.buildPath(pathList, root, stack);

        StringBuilder sb = new StringBuilder("DecisionTree: [\n");
        for (String s: pathList) {
            sb.append("    ").append(s).append("\n");
        }
        sb.append("]");
        return sb.toString();
    }

    private void buildPath(List<String> pathList, Object obj, Stack<Object> stack) {
        if (obj != null) {
            stack.push(obj);
            if (obj instanceof Node.Branch) {
                Node.Branch branch = (Node.Branch)obj;
                if (branch.node == null) {
                    this.changeToPath(stack, pathList);
                } else {
                    this.buildPath(pathList, branch.node, stack);
                }
            } else if(obj instanceof Node) {
                Node node = (Node)obj;
                if (node.branches.isEmpty()) {
                    this.changeToPath(stack, pathList);
                } else {
                    for (Node.Branch branch: node.branches) {
                        this.buildPath(pathList, branch, stack);
                    }
                }

            } else {
                throw new IllegalArgumentException("unknown type.");
            }
            stack.pop();
        }
    }

    public void changeToPath(Stack<Object> stack, List<String> pathList) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < stack.size(); i++) {
            sb.append(stack.get(i));
            if (i<stack.size()-1) {
                sb.append(" -> ");
            }
        }
        pathList.add(sb.toString().trim());
    }
}
