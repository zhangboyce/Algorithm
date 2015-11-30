package common.utils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Boyce
 * Date: 9/11/15
 * Time: 15:02
 * To change this template use File | Settings | File Templates.
 */
public abstract class AssertUtils {

    public static void assertNotNull(Object obj) {
        if (null == obj)
            throw new IllegalArgumentException();
    }

    public static void assertNotNull(Object obj, String msg) {
        if (null == obj)
            throw new IllegalArgumentException(msg);
    }

    public static void assertNotEmpty(String str) {
        if (StringUtils.isEmpty(str))
            throw new IllegalArgumentException();
    }

    public static void assertNotEmpty(String str, String msg) {
        if (StringUtils.isEmpty(str))
            throw new IllegalArgumentException(msg);
    }

    public static void assertNotEmpty(List<?> list) {
        assertNotEmpty(list, null);
    }

    public static void assertNotEmpty(List<?> list, String msg) {
        if (null == list || list.size() == 0)
            throw new IllegalArgumentException(msg);
    }

    public static void assertTrue(boolean val) {
        if (!val) throw new IllegalArgumentException();
    }

    public static void assertTrue(boolean val, String msg) {
        if (!val) throw new IllegalArgumentException(msg);
    }
}
