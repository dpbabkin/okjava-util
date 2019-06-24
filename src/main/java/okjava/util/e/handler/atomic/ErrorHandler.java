package okjava.util.e.handler.atomic;

import java.util.function.Consumer;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 11/26/2016
 * 11:06.
 */
@FunctionalInterface
public interface ErrorHandler<E extends Error> extends Consumer<E>, ThrowableHandler<E> {

    static <E extends Error> ErrorHandler<E> fromConsumer(Consumer<E> errorConsumer) {
        return errorConsumer::accept;
    }

    static <E extends Exception> ExceptionHandler<E> toExceptionHandler(ThrowableHandler<E> throwableHandler) {
        return throwableHandler::accept;
    }

    static Consumer<Runnable> createConsumer(ErrorHandler<Error> errorHandler) {
        Consumer<Runnable> consumer = runnable -> {
            try {
                runnable.run();
            } catch (Error error) {
                errorHandler.accept(error);
            }
        };
        return consumer;
    }
}
