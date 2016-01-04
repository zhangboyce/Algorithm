package institute;

import common.utils.FileUtils;

import java.util.*;

/**
 * User: Boyce
 * Date: 2/1/16
 * Time: 16:48
 */
public class Questions3 {

    private final static List<Data> trainData = loadTrainData();
    private final static List<Data> testData = loadTestData();

    public static void main(String[] args) {
        Map<String, Double> minErrorRateResult = null;
        int minErrorRateResultColumn = -1;
        List<Map<String, Double>> results = new ArrayList<Map<String, Double>>();
        for (int d=0; d<9; d++) {
            Map<String, Double> result = calculate(d);
            System.out.println(result);

            if (null == minErrorRateResult || result.get("errorRate") <= minErrorRateResult.get("errorRate")) {
                minErrorRateResult = result;
                minErrorRateResultColumn = d;
            }
            results.add(result);
        }

        System.out.println("best of best" + minErrorRateResult);
        test(minErrorRateResult, minErrorRateResultColumn);

        test(results);
    }

    private static void test(List<Map<String, Double>> results) {
        int errorCount = 0;
        for (int i=0; i<testData.size(); i++) {
            Data data = testData.get(i);
            double clazz = (double)data.clazz;

            double result = 0;
            for (int j=0; j<results.size(); j++) {
                double value = data.values.get(j);
                double theta = results.get(j).get("theta");
                double s = results.get(j).get("s");
                double errorRate = results.get(j).get("errorRate");
                double c = s*sign(value-theta);

                result += c * 0.5*(Math.log((1-errorRate)/errorRate)/Math.log(2));
            }

            if ((double)sign(result) != clazz)
                errorCount ++;
        }
        System.out.println("ada test data error rate: " +
                errorCount + "/" + testData.size() + "=" + ((double) errorCount / testData.size()));
    }

    private static void test(Map<String, Double> result, int column) {
        double s = result.get("s");
        double theta = result.get("theta");

        int errorCount = 0;
        for (int i=0; i<testData.size(); i++) {
            Data data = testData.get(i);
            double value = data.values.get(column);
            double clazz = (double)data.clazz;

            if (s*sign((value-theta)) != clazz)
                errorCount ++;
        }
        System.out.println("test data error rate: " +
                errorCount + "/" + testData.size() + "=" + ((double) errorCount / testData.size()));
    }

    private static Map<String, Double> calculate(int column) {
        sort(trainData, column);

        Map<String, Double> result1 = calculate(1, column);
        Map<String, Double> result2 = calculate(-1, column);

        if (result1.get("errorRate") <= result2.get("errorRate")) {
            result1.put("s", (double)1);
            return result1;
        } else {
            result2.put("s", (double)-1);
            return result2;
        }
    }

    private static Map<String, Double> calculate(int s, int column) {
        int errorCount = initRightErrorCount(s, column);
        int minErrorCount = errorCount;
        System.out.println("s = " + s);
        System.out.println("initRightErrorCount = " + minErrorCount);

        int index = -1;
        double theta = Integer.MIN_VALUE;
        for (int i=0; i< trainData.size(); i++) {
            Data nextData = trainData.get(i);
            double nextValue = nextData.values.get(column);
            int nextClazz = nextData.clazz;

            if (nextClazz == s*-1) {
                errorCount --;
                if (errorCount <= minErrorCount) {
                    index = i;
                    minErrorCount = errorCount;

                    if(index == trainData.size()-1) {
                        theta = Integer.MAX_VALUE;
                    } else {
                        theta = (nextValue + trainData.get(i+1).values.get(column))/2;
                    }
                }
            }
            if (nextClazz == s*1) {
                errorCount ++;
            }
        }
        double errorRate = (double)minErrorCount/trainData.size();
        System.out.println("minErrorCount/n = " + minErrorCount + "/" + trainData.size());
        System.out.println("theta = " + theta);
        System.out.println("å = " + Math.log((1-errorRate)/errorRate)/Math.log(2));
        System.out.println();

        Map<String, Double> result = new HashMap<String, Double>();
        result.put("errorRate", errorRate);
        result.put("theta", theta);
        return result;
    }

    // -3,-2,-1,2,3,4,5,7,
    private static int initRightErrorCount(int s, int column) {
        int errorCount = 0;
        double theta = trainData.get(0).values.get(column)-1;

        for (int i=0; i< trainData.size(); i++) {
            int clazz = trainData.get(i).clazz;
            double value = trainData.get(i).values.get(column);

            int sign = s*sign(value-theta);
            if (clazz != sign)
                errorCount ++;
        }
        return errorCount;
    }

    private static int sign(double value) {
        return value<0?-1:1;
    }

    private static void sort(List<Data> datas, final int dimensional) {
        Collections.sort(datas, new Comparator<Data>() {
            @Override
            public int compare(Data d1, Data d2) {
                return (d1.values.get(dimensional) - d2.values.get(dimensional))<0?-1:1;
            }
        });

        System.out.println("data sort by column " + dimensional + " :");
        for (Data data: datas) {
//            System.out.println(data.values.get(dimensional) + ", " + data.clazz);
        }
        System.out.println();
    }

    private static List<Data> loadTrainData() {
        String path = "data/hw2_train.txt";
        return loadData(path);
    }
    private static List<Data> loadTestData() {
        String path = "data/hw2_test.txt";
        return loadData(path);
    }

    private static List<Data> loadData(String path) {
        List<String> lines = FileUtils.readLines(Questions3.class.getResourceAsStream(path));

        List<Data> datas = new ArrayList<Data>();
        for (String line: lines) {
            String[] values = line.split(" ");
            Data data = new Data();
            List<Double> list = new ArrayList<Double>();
            int clazz = 0;
            for (int i=0; i<values.length; i++) {
                if (i != values.length-1) {
                    list.add(Double.valueOf(values[i]));
                } else
                    clazz = Integer.valueOf(values[i]);
            }

            data.values = list;
            data.clazz = clazz;
            datas.add(data);
        }
        return datas;
    }

    private static class Data {
        private List<Double> values;
        private int clazz;

        @Override
        public String toString() {
            return "Data{" +
                    "values=" + values +
                    ", clazz='" + clazz + '\'' +
                    '}';
        }
    }
}
