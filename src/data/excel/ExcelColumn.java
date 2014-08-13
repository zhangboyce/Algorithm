package data.excel;

/**
 * Created by boyce on 2014/7/2.
 */

@java.lang.annotation.Target({java.lang.annotation.ElementType.FIELD})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface ExcelColumn {
    String name() default "";
    int width() default 10;
}
