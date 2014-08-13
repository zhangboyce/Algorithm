package data.excel;

import common.utils.CollectionUtils;
import common.utils.StringUtils;
import jxl.Workbook;
import jxl.write.*;


import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by boyce on 2014/7/2.
 */
public class ExcelCreator {

    /**
     * Excel Data
     * @param <T>
     */
    public static class ExcelDataInfo<T> {
        private String name;
        private List<T> data;
        private Class<T> clazz;

        public ExcelDataInfo(String name, List<T> data, Class<T> clazz) {
            this.name = name;
            this.data = data;
            this.clazz = clazz;
            if (StringUtils.isEmpty(name) || CollectionUtils.isEmpty(data))
                throw new IllegalArgumentException("Construct a ExcelDataInfo error, name and data parameter cannot be empty.");

            if (null == clazz)
                throw new IllegalArgumentException("Construct a ExcelDataInfo error, clazz parameter cannot be null.");

            Annotation annotation = clazz.getAnnotation(ExcelData.class);
            if (null == annotation)
                throw new IllegalArgumentException("Construct a ExcelDataInfo error, clazz type without ExcelData annotation.");
        }
    }

    /**
     * 创建字体
     */
    //粗体
    private WritableFont blodFont;
    //普通字体
    private WritableFont normalFont;

    /**
     * 单元格属性
     */
    private WritableCellFormat wcf_header;
    private WritableCellFormat wcf_normal;

    /**
     * data
     */
    private ExcelDataInfo excelDataInfo;

    public ExcelCreator(ExcelDataInfo excelDataInfo) {
        this.excelDataInfo = excelDataInfo;
        if (null == excelDataInfo)
            throw new IllegalArgumentException("Construct a Excel error, excelDataInfo parameter cannot be null.");

        this.blodFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD ) ;
        this.normalFont = new WritableFont(WritableFont.ARIAL, 12);

        try {
            wcf_header = new WritableCellFormat(blodFont, NumberFormats.TEXT) ; // 实例化单元格格式对象（标题、居中）
            wcf_header.setBorder(Border.ALL, BorderLineStyle.THIN) ;    // 线条
            wcf_header.setVerticalAlignment(VerticalAlignment.CENTRE);     // 垂直对齐
            wcf_header.setAlignment(Alignment.CENTRE);                     // 水平对齐
            wcf_header.setBackground(Colour.GRAY_25) ;                    // 背景颜色
            wcf_header.setWrap(false);                                     // 是否换行

            wcf_normal = new WritableCellFormat(normalFont);                // 实例化单元格格式对象（正文、左对齐）
            wcf_normal.setBorder(Border.ALL, BorderLineStyle.THIN);         // 线条
            wcf_normal.setVerticalAlignment(VerticalAlignment.CENTRE);     // 垂直对齐
            wcf_normal.setAlignment(Alignment.LEFT);
            wcf_normal.setLocked(false) ;                                    // 设置锁定，还得设置SheetSettings启用保护和设置密码
            wcf_normal.setWrap(true);                                     // 是否换行

        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create Excel
     * @throws Exception
     */
    public void create() throws Exception {
        WritableWorkbook book = Workbook.createWorkbook(new File(excelDataInfo.name));
        WritableSheet sheet = book.createSheet("第一页", 0);

        //Get ExcelColumn field
        Field[] fields = excelDataInfo.clazz.getDeclaredFields();
        List<Field> excelColumnFields = new ArrayList<Field>();
        for (Field field: fields) {
            Annotation annotation = field.getAnnotation(ExcelColumn.class);
            if (annotation != null)
                excelColumnFields.add(field);
        }

        if (CollectionUtils.isNotEmpty(excelColumnFields)) {

            for (int i=0; i<excelColumnFields.size(); i++) {
                Field field = excelColumnFields.get(i);
                ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                int width = annotation.width();

                sheet.addCell(new Label(i, 0, field.getName(), this.wcf_header));
                sheet.setColumnView(i, width);

                for (int j=0; j<excelDataInfo.data.size(); j++) {
                    Object obj = excelDataInfo.data.get(j);

                    Method method = excelDataInfo.clazz.getMethod("get"
                            + StringUtils.upperFirstChar(field.getName()));

                    Object value = null;
                    if (null != method)
                        value = method.invoke(obj);

                    String cellValue = null == value ? "NULL" : value.toString();
                    sheet.addCell(new Label(i, j + 1, cellValue, this.wcf_normal));
                }
            }
        }

        book.write();
        book.close();

        System.out.println("Create Excel Successfully!");
    }
}
