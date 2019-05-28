package okjava.util.e;

import java.util.function.Consumer;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 11/26/2016
 * 11:06.
 */
@FunctionalInterface
public interface ThrowableHandler<E extends Throwable> extends Consumer<E> {

    static <E extends Throwable> ThrowableHandler<E> fromConsumer(Consumer<E> throwableConsumer) {
        return throwableConsumer::accept;
    }

    static <E extends Exception> ExceptionHandler<E> toExceptionHandler(ThrowableHandler<E> throwableHandler) {
        return throwableHandler::accept;
    }

    static <E extends Error> ErrorHandler<E> toErrorHandler(ThrowableHandler<E> throwableHandler) {
        return throwableHandler::accept;
    }
}
