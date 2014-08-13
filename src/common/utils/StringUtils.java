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

    public static final boolean isEquals(String str1, String str2) {
        if (null == str1 && str2 != null)
            return false;

        if (null != str1 && str2 == null)
            return false;

        if (null == str1 && str2 == null)
            return true;


        return str1.equals(str2);
    }

    public static String upperFirstChar(final String source) {
        if (isEmpty(source)) {
            return source;
        }

        // A ~ Z : 65 ~ 90; a ~ z : 97 ~ 122
        char[] array = source.toCharArray();

        // 字符串的第一个字母是小写的英文字母
        if (array[0] >= 97 && array[0] <= 122) {
            array[0] -= 32;

            return String.valueOf(array);
        }

        return source;

    }
}
