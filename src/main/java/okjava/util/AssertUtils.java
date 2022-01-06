package okjava.util;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.util.function.BiFunction;
import java.util.function.Function;

import static okjava.util.check.Never.neverNeverCalled;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 3/31/2018
 * 00:16.
 */
@Utility
public enum AssertUtils {
    ;
    private static final boolean isAssertEnabled = checkOnceIsAssertEnabled();

    AssertUtils(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static <E extends Throwable> E throwAfterAssert(String message, Function<String, E> exceptionConstructor) throws E {
        assert false : message;
        throw exceptionConstructor.apply(message);
    }

    public static IllegalArgumentException throwIllegalArgumentExceptionAfterAssert(String message) {
        return throwAfterAssert(message, IllegalArgumentException::new);
    }

    public static Error throwErrorAfterAssert(String message) {
        return throwAfterAssert(message, Error::new);
    }


    public static <E extends Exception> E throwAfterAssert(String message,
                                                           RuntimeException e,
                                                           BiFunction<String, RuntimeException, E> exceptionConstructor) throws E {
        proceedAssert(message, e);
        throw exceptionConstructor.apply(message, e);
    }

    public static void proceedAssert(String message, RuntimeException e) {
        if (isAssertEnabled()) {
            throw new java.lang.AssertionError(message, e);
        }
    }


    private static boolean checkOnceIsAssertEnabled() {
        try {
            assert false : "assert false";
            return false;
        } catch (java.lang.AssertionError assertionError) {
            return true;
        }
    }

    public static boolean isAssertEnabled() {
        return isAssertEnabled;
    }
}