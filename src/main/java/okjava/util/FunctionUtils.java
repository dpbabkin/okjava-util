package okjava.util;

import static okjava.util.check.Never.neverNeverCalled;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.util.function.Function;
import java.util.function.Supplier;


/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         6/3/2016
 *         22:27.
 */
@Utility
public final class FunctionUtils {
    private static final Function<?, ?> UNSUPPORTED_OPERATION_EXCEPTION_FUNCTION =
        t -> {
            throw new UnsupportedOperationException("not implemented");
        };

    private FunctionUtils(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    @SuppressWarnings("unchecked") //never cast
    public static <T, R> Function<T, R> unsupportedOperationException() {
        return (Function<T, R>) UNSUPPORTED_OPERATION_EXCEPTION_FUNCTION;
    }


    public static <T, R> Function<T, R> unsupportedOperationException(String message) {
        return exception(() -> {
            throw new UnsupportedOperationException(message);
        });
    }


    public static <T, R, E extends RuntimeException> Function<T, R> exception(Supplier<E> supplier) {
        return t -> {
            throw supplier.get();
        };
    }
}
