package bayesain.naive;

import common.utils.AssertUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Boyce
 * Date: 6/12/15
 * Time: 14:31
 * To change this template use File | Settings | File Templates.
 */

// 类别集合
public class Classifications {

    private List<Classification> classifications;
    private int totalCount;

    public Classifications() {
        this.totalCount = 0;
        this.classifications = new ArrayList<Classification>();
    }

    // 对指定的向量进行分类
    public Classification classify(XVector xVector) {
        AssertUtils.assertNotNull(xVector);
        Classification xVector_c = null;
        double maxProbability = 0.0;

        // max{p(Classification|XVector)}
        // p(Classification|XVector) = (p_XVector_Classification*p_classification)/p_XVector
        // p_XVector 对于每个Classification是常量，所以求 max {p_XVector_Classification*p_classification}
        for (Classification classification: classifications) {
            double p_classification = classification.p_Classification();
            double p_XVector_Classification = xVector.p_XVector_Classification(classification);

            double p_Classification_XVector = p_classification * p_XVector_Classification;
            System.out.println(classification.getName() + ": p_Classification_XVector > " + p_Classification_XVector);

            if (p_Classification_XVector > maxProbability) {
                maxProbability = p_Classification_XVector;
                xVector_c = classification;
            }
        }
        return xVector_c;
    }

    public void addClassification(Classification classification) {
        AssertUtils.assertNotNull(classification, "cannot add a null into Classifications.");

        classification.setClassifications(this);
        this.classifications.add(classification);

        this.totalCount += classification.getCount();
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public int getClassificationSize() {
        return this.classifications.size();
    }
}
