package okjava.util;

import static okjava.util.check.Never.neverNeverCalled;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;
import okjava.util.e.EFunction;
import okjava.util.e.handler.atomic.ExceptionHandler;
import okjava.util.exception.SneakyThrower;

import java.util.function.Function;
import java.util.function.Supplier;


/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/3/2016
 * 22:27.
 */
@Utility
public enum FunctionUtils {
    ;

    private static final Function<?, ?> UNSUPPORTED_OPERATION_EXCEPTION_FUNCTION = t -> {
        throw new UnsupportedOperationException("not implemented");
    };

    FunctionUtils(@SuppressWarnings("unused") Never never) {
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

    public static <V1, V2, E extends Exception> Function<V1, V2> divertExceptionFromEFunction(EFunction<V1, V2, E> eFunction, ExceptionHandler<E> exceptionHandler) {

        Function<V1, V2> result = value -> {
            try {
                return eFunction.apply(value);
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                try {
                    exceptionHandler.accept((E) e);
                } catch (ClassCastException e1) {
                    //hm... not declared checked exception type thrown.
                    SneakyThrower.sneakyThrow(e);
                }
            }
            throw new Error("should never happen");
        };
        return result;
    }
}
