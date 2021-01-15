package okjava.util.e;

import java.util.function.BiFunction;

import static java.util.Objects.requireNonNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 10/3/2015
 * 01:33.
 */
@FunctionalInterface
public interface EBiFunction<T, U, R, E extends Exception> {

    static <T, U, R> EBiFunction<T, U, R, RuntimeException> delegate(BiFunction<T, U, R> biFunction) {
        requireNonNull(biFunction);
        return biFunction::apply;
    }

    R apply(T t, U u) throws E;
}
