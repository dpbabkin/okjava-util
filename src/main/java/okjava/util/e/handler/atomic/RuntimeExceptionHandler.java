package okjava.util.e.handler.atomic;

import java.util.function.Consumer;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 11/26/2016
 * 11:06.
 */
@FunctionalInterface
public interface RuntimeExceptionHandler<E extends RuntimeException> extends ExceptionHandler<E> {

    static <E extends RuntimeException> RuntimeExceptionHandler<E> fromConsumer(Consumer<RuntimeException> runtimeExceptionConsumer) {
        return runtimeExceptionConsumer::accept;
    }

    static Consumer<Runnable> createConsumer(RuntimeExceptionHandler<RuntimeException> runtimeExceptionHandler) {
        Consumer<Runnable> consumer = runnable -> {
            try {
                runnable.run();
            } catch (RuntimeException runtimeException) {
                runtimeExceptionHandler.accept(runtimeException);
            }
        };
        return consumer;
    }
}
