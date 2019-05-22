package okjava.util.e;

import static java.util.Objects.requireNonNull;

import java.util.function.Consumer;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 10/3/2015
 * 01:33.
 */
@FunctionalInterface
public interface EConsumer<T, E extends Exception> {

    static <T> EConsumer<T, RuntimeException> delegate(Consumer<T> consumer) {
        requireNonNull(consumer);
        return consumer::accept;
    }

    void accept(T a) throws E;
}
