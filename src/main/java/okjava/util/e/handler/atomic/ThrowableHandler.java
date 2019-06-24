package okjava.util.e.handler.atomic;

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

    static Consumer<Runnable> createConsumer(ThrowableHandler<Throwable> throwableHandler) {
        Consumer<Runnable> consumer = runnable -> {
            try {
                runnable.run();
            } catch (Throwable throwable) {
                throwableHandler.accept(throwable);
            }
        };
        return consumer;
    }
}
