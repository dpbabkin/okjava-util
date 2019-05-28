package okjava.util.e;

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
}
