package okjava.util.e;

import java.util.function.Consumer;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 11/26/2016
 * 11:06.
 */
@FunctionalInterface
public interface ExceptionHandler<E extends Exception> extends Consumer<E>, ThrowableHandler<E> {

    static <E extends Exception> ExceptionHandler<E> fromConsumer(Consumer<E> exceptionConsumer) {
        return exceptionConsumer::accept;
    }

    static <E extends RuntimeException> RuntimeExceptionHandler<E> toRuntimeExceptionHandler(ExceptionHandler<E> exceptionHandler) {
        return exceptionHandler::accept;
    }
}
