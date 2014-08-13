package data.entity;

import data.excel.ExcelColumn;
import data.excel.ExcelData;
import json.JsonBuilderExecutor;

/**
 * Created by boyce on 2014/7/2.
 */
@ExcelData
public class Product {

    private Object _id;

    @ExcelColumn(width = 80)
    private Object name;
    @ExcelColumn
    private Object brand;
    @ExcelColumn(width = 100)
    private Object category;
    @ExcelColumn
    private Object comment;
    @ExcelColumn
    private Object crawBrand;
    @ExcelColumn
    private Object currentPrice;
    @ExcelColumn
    private Object yhdPrice;
    @ExcelColumn
    private Object price;
    @ExcelColumn
    private Object shop;

    public Object get_id() {
        return _id;
    }

    public void set_id(Object _id) {
        this._id = _id;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getBrand() {
        return brand;
    }

    public void setBrand(Object brand) {
        this.brand = brand;
    }

    public Object getCategory() {
        return category;
    }

    public void setCategory(Object category) {
        this.category = category;
    }

    public Object getComment() {
        return comment;
    }

    public void setComment(Object comment) {
        this.comment = comment;
    }

    public Object getCrawBrand() {
        return crawBrand;
    }

    public void setCrawBrand(Object crawBrand) {
        this.crawBrand = crawBrand;
    }

    public Object getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Object currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Object getYhdPrice() {
        return yhdPrice;
    }

    public void setYhdPrice(Object yhdPrice) {
        this.yhdPrice = yhdPrice;
    }

    public Object getPrice() {
        return price;
    }

    public void setPrice(Object price) {
        this.price = price;
    }

    public Object getShop() {
        return shop;
    }

    public void setShop(Object shop) {
        this.shop = shop;
    }

    @Override
    public String toString() {
       return JsonBuilderExecutor.getInstance().toJson(this);
    }
}
