package okjava.util;

import static okjava.util.check.Never.neverNeverCalled;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;


/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         6/3/2016
 *         22:27.
 */
@Utility
public enum NotEmpty {
    ;

    NotEmpty(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static String notEmptyString(String string) {
        return check(string);
    }

    public static String check(String string) {
        if (Objects.requireNonNull(string, "null string").length() == 0) {
            throw new IllegalArgumentException("empty string");
        }
        return string;
    }

    public static <T> Collection<T> check(Collection<T> collection) {
        if (Objects.requireNonNull(collection, "null collection").size() == 0) {
            throw new IllegalArgumentException("empty collection");
        }
        return collection;
    }

    public static <T> Iterable<T> check(Iterable<T> iterable) {
        if (!Objects.requireNonNull(iterable, "null iterable").iterator().hasNext()) {
            throw new IllegalArgumentException("empty iterable");
        }
        return iterable;
    }

    public static <K, V> Map<K, V> check(Map<K, V> map) {
        if (Objects.requireNonNull(map, "null collections").size() == 0) {
            throw new IllegalArgumentException("empty collections");
        }
        return map;
    }
}
