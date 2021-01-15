package okjava.util.check;

import okjava.util.annotation.Utility;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;
import static okjava.util.check.Never.fail;
import static okjava.util.check.Never.neverNeverCalled;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/15/2016
 * 19:44.
 */
@Utility
public enum Once {
    ;

    private static final Object OBJECT = new Object();
    private static final Set<Class<?>> CLASS_MAP = ConcurrentHashMap.newKeySet();

    @SuppressWarnings("unused")
    Once(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    /**
     * Make sure that method with this argument called only once per JVM.
     * Intended to assert that class constructor called only once for Singleton instances.
     *
     * @param clazz - argument to keep tracking.
     */
    public static void calledOnce(Class<?> clazz) {
        requireNonNull(clazz);
        boolean value = CLASS_MAP.add(clazz);
        if (value == false) {
            fail("Second initialization detected for class=" + clazz);
        }
    }
}
