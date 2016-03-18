package institute;

import common.utils.FileUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * User: Boyce
 * Date: 2/1/16
 * Time: 16:48
 */
public class Questions2 {

    public static void main(String[] args) {
        List<Product> trainProducts = loadTrainData();

        // 设评论和销量大概满足线性关系 y = a*x + b 的线性关系
        // 参数a,b的最小二乘估计 a = avg(Y)-b*avg(X), b=L(xy)/L(xx)
        // 其中 L(xx)=(X(i)-avg(X))², i=1,2...n, L(xy)=(X(i)-avg(X))*(Y(i)-avg(Y)), i=1,2...n
        int n = 0;
        int x_total = 0;
        int x_total_sq = 0;
        int y_total = 0;
        int xy_total = 0;
        for (Product product: trainProducts) {
            if (product.totalComment == 0 && product.totalSale >= 500) {
                continue;
            }
            if (product.totalComment == 699 && product.totalSale==288) {
                continue;
            }
            x_total += product.totalComment;
            x_total_sq += (product.totalComment*product.totalComment);
            y_total += product.totalSale;
            xy_total += (product.totalComment*product.totalSale);
            n ++;
        }
        double x_avg = (double)x_total/n;
        double y_avg = (double)y_total/n;

        double l_xx = x_total_sq - n*(x_avg*x_avg);
        double l_xy = xy_total - n*(x_avg*y_avg);

        double b = l_xy/l_xx;
        double a = y_avg - b*x_avg;

        System.out.println(a + "*x + " + b);

        // y = 4.84*x + 3.57
        // y = 5.16*x + 3.57
        // y = 4.91*x + 3.57
        // y = 3.82*x + 3.68

        List<Product> testProducts = loadTestData();
        System.out.println("name, id, totalComment, totalSale");
        for (Product product: testProducts) {
            System.out.println(product.name + ", " + product.id + ", "
                    + product.totalComment + ", " + String.format("%.2f", (product.totalComment*a + b)));
        }
    }

    private static List<Product> loadTestData() {
        List<String> lines = FileUtils.readLines(Questions2.class.getResourceAsStream("data/mp_sale_comment_2.csv"));
        List<Product> products = new ArrayList<Product>();
        for (int i=1;i<lines.size(); i++) {
            String line = lines.get(i);

            String[] values = line.split(",");
            Product product = new Product();
            product.name = values[0];
            product.id = values[1];
            product.totalComment = Integer.valueOf(values[2]);
            product.net = Double.valueOf(values[3]);

            products.add(product);
        }
        return products;
    }

    private static List<Product> loadTrainData() {
        List<String> lines = FileUtils.readLines(Questions2.class.getResourceAsStream("data/mp_sale_comment_1.csv"));
        List<Product> products = new ArrayList<Product>();
        for (int i=1;i<lines.size(); i++) {
            String line = lines.get(i);

            String[] values = line.split(",");
            Product product = new Product();
            product.name = values[0];
            product.id = values[1];
            product.totalSale = Integer.valueOf(values[2]);
            product.totalComment = Integer.valueOf(values[3]);
            product.totalRevenue = Double.valueOf(values[4]);
            product.net = Double.valueOf(values[5]);

            products.add(product);

            System.out.println(product.totalComment + "," + product.totalSale);
        }
        return products;
    }

    private static class Product {
        private String name;
        private String id;
        private int totalSale;
        private int totalComment;
        private double totalRevenue;
        private double net;

        @Override
        public String toString() {
            return "Product{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", totalComment=" + totalComment +
                    ", totalSale=" + totalSale +
                    ", totalRevenue=" + totalRevenue +
                    ", net=" + net +
                    '}';
        }
    }
}
