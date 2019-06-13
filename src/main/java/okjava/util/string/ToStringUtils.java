package okjava.util.string;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/5/2016
 * 00:37.
 */
@Utility
public enum ToStringUtils {
    ;

    private static final String NULL = "null";
    //    public static String eq(Object one, Object two) {
//        return one + "=" + two;
//    }
    private static final Function<Object, String> TO_STRING_MAPPER = ToStringUtils::nullable;
    private static final Function<String, String> STRING_TO_STRING_MAPPER = TO_STRING_MAPPER::apply;
    private static final String DEFAULT_SEPARATOR = " ";

    ToStringUtils(@SuppressWarnings("unused") Never never) {
        Never.neverNeverCalled();
    }

    public static String nullable(Object nullable) {
        return nullable != null ? nullable.toString() : NULL;
    }

    @SuppressWarnings("unchecked")
    private static <K> Function<K, String> createStringVisitor() {
        return (Function<K, String>) TO_STRING_MAPPER;
    }


    public static <K, V> String m2s(Map<K, V> map) {
        ToStringBuffer stringBuffer = ToStringBuffer.string("map");
        map.forEach((key, value) -> stringBuffer.add(nullable(key), value));
        return stringBuffer.toString();
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
        return StreamSupport.stream(iterable.spliterator(), false).map(mapper).collect(Collectors.joining(separator));
    }


    public static String is2s(Iterable<String> iterable) {
        return i2s(iterable, STRING_TO_STRING_MAPPER, DEFAULT_SEPARATOR);
    }


    public static String is2s(Iterable<String> iterable, String separator) {
        return i2s(iterable, STRING_TO_STRING_MAPPER, separator);
    }
}
