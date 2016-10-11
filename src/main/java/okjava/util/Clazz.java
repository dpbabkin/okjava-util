package okjava.util;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;


/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         6/1/2016
 *         22:52.
 */
@Utility
public final class Clazz {
    private Clazz(Never never) {
        Never.neverCalled();
    }


    @SuppressWarnings("unchecked")
    public static <T> Class<T> cast(Class<?> clazz) {
        return (Class<T>) (Class) clazz;
    }
}
