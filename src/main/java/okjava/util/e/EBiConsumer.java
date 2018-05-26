package okjava.util.e;

import static java.util.Objects.requireNonNull;

import java.util.function.BiConsumer;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 10/3/2015
 * 01:33.
 */
@FunctionalInterface
public interface EBiConsumer<T, U, E extends Exception> {

    static <T, U> EBiConsumer<T, U, RuntimeException> delegate(BiConsumer<T, U> consumer) {
        requireNonNull(consumer);
        return consumer::accept;
    }

    void accept(T a, U u) throws E;
}
