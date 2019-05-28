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
}
