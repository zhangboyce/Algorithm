package data;

import com.mongodb.*;
import common.json.JsonBuilderExecutor;
import data.db.DBCollectionCreator;
import data.entity.Product;
import data.excel.ExcelCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boyce on 2014/7/7.
 */
public class Product2Excel {
    public static void main(String[] args) {
        try {
            DBCollection dbCollection = DBCollectionCreator.newDBCollection("product");
            DBObject dBObject = new BasicDBObject("brand", "清扬");
            DBCursor dbCursor = dbCollection.find(dBObject);

            List<Product> products = new ArrayList<Product>();
            while (dbCursor.hasNext()) {
                DBObject it = dbCursor.next();
                Product product = JsonBuilderExecutor.getInstance().toObject(it.toString(), Product.class);
                products.add(product);

                System.out.println(product);
            }

            ExcelCreator excel = new ExcelCreator(new ExcelCreator.ExcelDataInfo<Product>("product_"+ System.currentTimeMillis() +".xls", products, Product.class));
            excel.create();

        } catch(Exception e) {
            e.printStackTrace();
        }


    }
}
