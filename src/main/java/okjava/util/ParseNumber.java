package okjava.util;

import static okjava.util.OptionalUtils.or;
import static okjava.util.check.Never.neverNeverCalled;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         6/4/2016
 *         23:31.
 */
@Utility
public enum ParseNumber {
    ;

    ParseNumber(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }


    public static <T extends Number> Optional<T> parseObject(Object value, Function<String, T> function) {
        try {
            if (value != null) {
                return Optional.of(function.apply(value.toString()));
            }
        } catch (NumberFormatException e) {
            //nothing to do
        }
        return Optional.empty();
    }


    public static <T extends Number> Optional<T> parseNumber(Object value, Function<Number, T> function) {
        if (value instanceof Number) {
            return Optional.of(function.apply((Number) value));
        }
        return Optional.empty();
    }


    public static Optional<Long> parseLong(Object value) {
        return or(parseNumber(value, Number::longValue), parseObject(value, Long::parseLong));
    }


    public static Optional<Integer> parseInt(Object value) {
        return or(parseNumber(value, Number::intValue), parseObject(value, Integer::parseInt));
    }
}
