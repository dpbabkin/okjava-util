package okjava.util.e.handler.atomic;

import java.util.function.Consumer;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 11/26/2016
 * 11:06.
 */
@FunctionalInterface
public interface ExceptionHandler<E extends Exception> extends ThrowableHandler<E> {

    static <E extends Exception> ExceptionHandler<E> fromConsumer(Consumer<E> exceptionConsumer) {
        return exceptionConsumer::accept;
    }

    static <E extends RuntimeException> RuntimeExceptionHandler<E> toRuntimeExceptionHandler(ExceptionHandler<E> exceptionHandler) {
        return exceptionHandler::accept;
    }


    static Consumer<Runnable> createConsumer(ExceptionHandler<Exception> exceptionHandler) {
        Consumer<Runnable> consumer = runnable -> {
            try {
                runnable.run();
            } catch (Exception exception) {
                exceptionHandler.accept(exception);
            }
        };
        return consumer;
    }
}
