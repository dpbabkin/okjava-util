package okjava.util;

import static okjava.util.check.Never.neverNeverCalled;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.util.function.Function;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 3/31/2018
 * 00:16.
 */
@Utility
public enum AfterAssert {
    ;

    AfterAssert(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static <E extends Exception> E throwAfterAssert(String message, Function<String, E> exceptionConstructor) throws E {
        assert false : message;
        throw exceptionConstructor.apply(message);
    }

    public static IllegalArgumentException throwIllegalArgumentExceptionAfterAssert(String message) {
        return throwAfterAssert(message, IllegalArgumentException::new);
    }
}