package okjava.util.check;

import static com.google.common.collect.Maps.newConcurrentMap;
import static java.util.Objects.requireNonNull;
import static okjava.util.check.Never.fail;
import static okjava.util.check.Never.neverCalled;

import okjava.util.annotation.Utility;

import java.util.concurrent.ConcurrentMap;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         6/15/2016
 *         19:44.
 */
@Utility
public final class Once {

    private static final Object OBJECT = new Object();
    private static final ConcurrentMap<Class<?>, Object> CLASS_MAP = newConcurrentMap();

    private Once(Never never) {
        neverCalled();
    }

    /**
     * Make sure that method with this argument called only once per JVM.
     * Intended to assert that class constructor called only once for Singleton instances.
     *
     * @param clazz - argument to keep tracking.
     */
    public static void calledOnce(Class<?> clazz) {
        requireNonNull(clazz);
        Object value = CLASS_MAP.putIfAbsent(clazz, OBJECT);
        if (value != null) {
            fail("Second initialization detected for class=" + clazz);
        }
    }
}
