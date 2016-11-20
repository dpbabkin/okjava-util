package okjava.util;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         6/5/2016
 *         00:37.
 */
@Utility
public final class ToStringUtils {
    private static final Function<Object, String> TO_STRING_MAPPER = o -> o == null ? "null" : o.toString();
    private static final Function<String, String> STRING_TO_STRING_MAPPER = o -> o == null ? "null" : o;
    private static final String DEFAULT_SEPARATOR = " ";

    private ToStringUtils(@SuppressWarnings("unused") Never never) {
        Never.neverNeverCalled();
    }

    @SuppressWarnings("unchecked")
    private static <K> Function<K, String> createStringVisitor() {
        return (Function<K, String>) TO_STRING_MAPPER;
    }


    public static <K> String i2s(Iterable<K> iterable) {
        return i2s(iterable, createStringVisitor(), DEFAULT_SEPARATOR);
    }


    public static <K> String i2s(Iterable<K> iterable, Function<K, String> mapper) {
        return i2s(iterable, mapper, DEFAULT_SEPARATOR);
    }


    public static <K> String i2s(Iterable<K> iterable, String separator) {
        return i2s(iterable, createStringVisitor(), separator);
    }


    public static <K> String i2s(Iterable<K> iterable, Function<K, String> mapper, String separator) {
        if (iterable == null) {
            return "null";
        }
        return StreamSupport.stream(iterable.spliterator(), false).map(mapper::apply).collect(Collectors.joining(separator));
    }


    public static String is2s(Iterable<String> iterable) {
        return i2s(iterable, STRING_TO_STRING_MAPPER, DEFAULT_SEPARATOR);
    }


    public static String is2s(Iterable<String> iterable, String separator) {
        return i2s(iterable, STRING_TO_STRING_MAPPER, separator);
    }
}
