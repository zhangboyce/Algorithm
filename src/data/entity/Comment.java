package data.entity;

import common.json.JsonBuilderExecutor;
import data.excel.ExcelColumn;
import data.excel.ExcelData;

/**
 * Created by boyce on 2014/7/7.
 */
@ExcelData
public class Comment {

    private Object _id;

    @ExcelColumn(width = 160)
    private Object Content;

    public Object getContent() {
        return Content;
    }

    public void setContent(Object content) {
        this.Content = content;
    }

    public Object get_id() {
        return _id;
    }

    public void set_id(Object _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return JsonBuilderExecutor.getInstance().toJson(this);
    }
}
