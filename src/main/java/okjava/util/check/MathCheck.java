package okjava.util.check;

import static okjava.util.check.Never.neverNeverCalled;

import okjava.util.annotation.Utility;
import okjava.util.function.IntBiFunction;
import okjava.util.function.LongBiFunction;

import java.util.function.IntFunction;
import java.util.function.LongFunction;


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


    private static final IntFunction<IllegalArgumentException> NON_NEGATIVE_EXCEPTION_FUNCTION_INT = (IntFunction<IllegalArgumentException>) value -> {
        throw new IllegalArgumentException("value must be non negative. value=" + value);
    };

    private static final LongFunction<IllegalArgumentException> NON_NEGATIVE_EXCEPTION_FUNCTION_LONG = (LongFunction<IllegalArgumentException>) value -> {
        throw new IllegalArgumentException("value must be non negative. value=" + value);
    };


    private static final IntBiFunction<IllegalArgumentException> LESS_THEN_OR_EQUALS_EXCEPTION_FUNCTION_INT = (IntBiFunction<IllegalArgumentException>) (left, right) -> {
        throw new IllegalArgumentException("value " + left + " must be less then or equal to " + right);
    };

    private static final LongBiFunction<IllegalArgumentException> LESS_THEN_OR_EQUALS_EXCEPTION_FUNCTION_LONG = (LongBiFunction<IllegalArgumentException>) (left, right) -> {
        throw new IllegalArgumentException("value " + left + " must be less then or equal to " + right);
    };


    private static final IntBiFunction<IllegalArgumentException> LESS_THEN_EXCEPTION_FUNCTION_INT = (IntBiFunction<IllegalArgumentException>) (left, right) -> {
        throw new IllegalArgumentException("value " + left + " must be less then " + right);
    };


    private static final LongBiFunction<IllegalArgumentException> LESS_THEN_EXCEPTION_FUNCTION_LONG = (LongBiFunction<IllegalArgumentException>) (left, right) -> {
        throw new IllegalArgumentException("value " + left + " must be less then " + right);
    };


    public static int nonNegative(int value) {
        return nonNegative(value, NON_NEGATIVE_EXCEPTION_FUNCTION_INT);
    }


    public static long nonNegative(long value) {
        return nonNegative(value, NON_NEGATIVE_EXCEPTION_FUNCTION_LONG);
    }


    public static <E extends Exception> int nonNegative(int value, IntFunction<E> exceptionIntFunction) throws E {
        lessThenOrEqual(0, value, (IntBiFunction<E>) (first, second) -> exceptionIntFunction.apply(second));
        return value;
    }

    public static <E extends Exception> long nonNegative(long value, LongFunction<E> exceptionIntFunction) throws E {
        lessThenOrEqual(0L, value, (first, second) -> exceptionIntFunction.apply(second));
        return value;
    }


    public static void lessThen(int left, int right) {
        lessThen(left, right, LESS_THEN_EXCEPTION_FUNCTION_INT);
    }

    public static void lessThen(long left, long right) {
        lessThen(left, right, LESS_THEN_EXCEPTION_FUNCTION_LONG);
    }


    public static <E extends Exception> void lessThen(int left, int right, IntBiFunction<E> exceptionIntFunction) throws E {
        if (left >= right) {
            throw exceptionIntFunction.apply(left, right);
        }
    }


    public static <E extends Exception> void lessThen(long left, long right, LongBiFunction<E> exceptionIntFunction) throws E {
        if (left >= right) {
            throw exceptionIntFunction.apply(left, right);
        }
    }


    public static void lessThenOrEqual(int left, int right) {
        lessThenOrEqual(left, right, LESS_THEN_OR_EQUALS_EXCEPTION_FUNCTION_INT);
    }


    public static void lessThenOrEqual(long left, long right) {
        lessThenOrEqual(left, right, LESS_THEN_OR_EQUALS_EXCEPTION_FUNCTION_LONG);
    }


    public static <E extends Exception> void lessThenOrEqual(int left, int right, IntBiFunction<E> exceptionIntFunction) throws E {
        if (left > right) {
            throw exceptionIntFunction.apply(left, right);
        }
    }

    public static <E extends Exception> void lessThenOrEqual(long left, long right, LongBiFunction<E> exceptionIntFunction) throws E {
        if (left > right) {
            throw exceptionIntFunction.apply(left, right);
        }
    }
}
