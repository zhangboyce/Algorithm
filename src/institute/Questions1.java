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
public class Questions1 {

    public static void main(String[] args) {
        List<String> lines = FileUtils.readLines(Questions1.class.getResourceAsStream("data/mp_sale_comment_1.csv"));

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
        }

        Collections.sort(products, new Comparator<Product>(){
            @Override
            public int compare(Product product, Product product2) {
                return product2.totalComment - product.totalComment;
            }
        });

        System.out.println("top10 totalComment:");
        for (int i=0; i<10; i++) {
            Product product = products.get(i);
            System.out.println(product.id + ", " + product.name + ", " + product.totalComment);
        }

        Collections.sort(products, new Comparator<Product>(){
            @Override
            public int compare(Product product, Product product2) {
                return product2.totalSale - product.totalSale;
            }
        });

        System.out.println("top10 totalSale:");
        for (int i=0; i<10; i++) {
            Product product = products.get(i);
            System.out.println(product.id + ", " + product.name + ", " + product.totalSale);
        }

        Collections.sort(products, new Comparator<Product>(){
            @Override
            public int compare(Product product, Product product2) {
                return (int)(product2.totalRevenue - product.totalRevenue);
            }
        });

        System.out.println("top10 totalRevenue:");
        for (int i=0; i<10; i++) {
            Product product = products.get(i);
            System.out.println(product.id + ", " + product.name + ", " + product.totalRevenue);
        }
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
