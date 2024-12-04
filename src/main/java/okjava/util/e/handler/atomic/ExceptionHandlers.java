package okjava.util.e.handler.atomic;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import static okjava.util.e.handler.atomic.EmptyExceptionHandler.emptyExceptionHandler;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 13.01.2022 - 21:07.
 */
@Utility
public class ExceptionHandlers {
    ;

    ExceptionHandlers(@SuppressWarnings("unused") Never never) {
        Never.neverNeverCalled();
    }

    public static ExceptionHandler<Exception> createRethrow() {
        return emptyExceptionHandler();
    }
}
