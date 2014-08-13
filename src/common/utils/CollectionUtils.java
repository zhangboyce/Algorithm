package common.utils;

import structure.list.IArrayList;
import structure.list.IList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * Created by boyce on 2014/7/28.
 */
public abstract class CollectionUtils {

    public static <T> boolean isEmpty(IList<T> ts) {
        return null == ts || ts.isEmpty();
    }

    public static <T> boolean isNotEmpty(IList<T> ts) {
        return !isEmpty(ts);
    }

    public static boolean isEmpty(Collection collection) {
        return null == collection && collection.size() == 0;
    }

    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }

}
