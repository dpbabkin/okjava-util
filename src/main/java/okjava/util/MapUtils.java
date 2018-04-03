package okjava.util;

import static okjava.util.check.Never.neverNeverCalled;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.util.Map;
import java.util.function.Function;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 3/30/2018
 * 21:50.
 */
@Utility
public enum MapUtils {
    ;

    MapUtils(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static <K, V, E extends Exception> V getOrThrow(Map<K, V> map, K key, Function<K, E> exceptionProvider) throws E {
        V value = map.get(key);
        if (value != null) {
            return value;
        }
        throw exceptionProvider.apply(key);
    }
}