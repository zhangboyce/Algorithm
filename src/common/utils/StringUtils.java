package common.utils;

/**
 * Created by boyce on 2014/7/27.
 */
public abstract class StringUtils {

    public static boolean isEmpty(String source) {
        return null == source || source.isEmpty();
    }

    public static boolean isNotEmpty(String source) {
        return !isEmpty(source);
    }
}
