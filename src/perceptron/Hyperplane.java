package perceptron;

import common.utils.AssertUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Boyce
 * Date: 21/12/15
 * Time: 23:21
 *
 * 感知机分类，超平面
 */
public class Hyperplane {
    // 超平面权值向量
    private double[] w;
    // 超平面偏置
    private double b;
    // 超平面维数
    private int dimensional;
    // w 和 b的梯度下滑学习步长
    private double learningRate;

    /**
     * 超平面 w*x + b = 0
     * y = sign(w*x + b), 当w*x + b>0,y=1;w*x + b<0,y=-1
     * 所以误分类点  y(w*x + b)<0
     * 误差函数 L(w,b) = y(i)(w*x(i))+b, i=1,2...n
     * 求最小误差函数，分别在w,b上求偏导得：
     * w' = -x(i)y(i), i=1,2...n
     * b' = -y(i), i=1,2...n
     * 对于每一个误分类数据(x,y), w和b分别按照偏导梯度以及步长更新
     * @param dimensional
     * @param learningRate
     */
    public Hyperplane(int dimensional, double learningRate) {
        AssertUtils.assertTrue(dimensional >= 2, "cannot construct a Hyperplane, the dimensional must be more than or equals 2.");
        this.dimensional = dimensional;
        this.learningRate = learningRate;

        // 初始化b=0 w=0
        this.b = 0;
        this.w = new double[dimensional];
        for (int i=0; i<dimensional; i++) {
            this.w[i] = 0;
        }
    }

    /**
     * 如果x被该超平面误分类，则梯度下滑更新该超平面参数w和b，直到该x点被正确分类。
     * 最后直到所有训练集被正确分类。
     * @param x 超平面中的点x
     * @param y x的标记分类
     * @return
     */
    public boolean learn(double[] x, int y) {
        if (null == x || x.length != this.dimensional)
            throw new IllegalArgumentException("");

        double wx = 0;
        for (int i=0; i<this.dimensional; i++) {
            wx += w[i]*x[i];
        }
        double result = y*(wx + b);
        if (result <= 0) {
            this.update(x, y);
            this.learn(x,y);
        }

        return result <= 0;
    }

    public void train(Map<double[], Integer> trainingMap) {
        if (null == trainingMap || trainingMap.isEmpty())
            return;

        for (Map.Entry<double[], Integer> entry: trainingMap.entrySet()) {
            boolean correct = this.learn(entry.getKey(), entry.getValue());
            while (!correct) {
                this.update(entry.getKey(), entry.getValue());
                correct = this.learn(entry.getKey(), entry.getValue());
            }
        }
    }

    // 梯度下滑更新w和b
    // w' = -x(i)y(i), i=1,2...n
    // b' = -y(i), i=1,2...n
    private void update(double[] x, int y) {
        // b = b + R*y
        this.b += this.learningRate*y;

        // w = w + R*y*x
        for (int i=0; i<x.length; i++) {
            this.w[i] += this.learningRate * x[i] * y;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<this.dimensional; i++) {
            sb.append(this.w[i] + "*x("+(i+1) + ")+");
        }
        sb.append(this.b).append(" = 0");
        return sb.toString();
    }

    public static void main(String[] args) {
        Hyperplane hyperplane = new Hyperplane(2, 1);
        System.out.println(hyperplane);

        double[] x1 = {1,1};
        double[] x2 = {1,2};
        double[] x3 = {1,3};
        double[] x4 = {2,2};
        double[] x5 = {2,3};

        double[] x6 = {3,1};
        double[] x7 = {4,1};
        double[] x8 = {4,2};
        double[] x9 = {4,3};

        Map<double[], Integer> trainingMap = new HashMap<double[], Integer>();
        trainingMap.put(x1, 1);
        trainingMap.put(x2, 1);
        trainingMap.put(x3, 1);
        trainingMap.put(x4, 1);
        trainingMap.put(x5, 1);

        trainingMap.put(x6, -1);
        trainingMap.put(x7, -1);
        trainingMap.put(x8, -1);
        trainingMap.put(x9, -1);

        for (Map.Entry<double[], Integer> entry: trainingMap.entrySet()) {

        }

        System.out.println(hyperplane);
    }
}
