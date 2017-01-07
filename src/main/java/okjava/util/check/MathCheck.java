package okjava.util.check;

import static okjava.util.check.Never.neverNeverCalled;

import okjava.util.annotation.Utility;

import java.util.function.IntFunction;


/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         6/3/2016
 *         22:27.
 */
@Utility
public enum MathCheck {
    ;

    MathCheck(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    private static final IntFunction<IllegalArgumentException> NON_NEGATIVE_EXCEPTION_FUNCTION = (IntFunction<IllegalArgumentException>) value -> {
        throw new IllegalArgumentException("value must be non negative. value=" + value);
    };

    public static int nonNegative(int value) {
        return nonNegative(value, NON_NEGATIVE_EXCEPTION_FUNCTION);
    }

    public static <E extends Exception> int nonNegative(int value, IntFunction<E> exceptionIntFunction) throws E {
        if (value < 0) {
            throw exceptionIntFunction.apply(value);
        }
        return value;
    }


    public static <E extends Exception> int lessThan(int value, int limit, IntFunction<E> exceptionIntFunction) throws E {
        if (value >= limit) {
            throw exceptionIntFunction.apply(value);
        }
        return value;
    }

    public static <E extends Exception> int lessThanOrEqual(int value, int limit, IntFunction<E> exceptionIntFunction) throws E {
        if (value > limit) {
            throw exceptionIntFunction.apply(value);
        }
        return value;
    }
}
