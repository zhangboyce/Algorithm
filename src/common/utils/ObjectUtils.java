package common.utils;

/**
 * Created by boyce on 2014/7/29.
 */
public abstract class ObjectUtils {
    public static boolean isNull(Object t) {
        return t == null;
    }

    public static boolean isNotNull(Object t) {
        return !isNull(t);
    }
}
