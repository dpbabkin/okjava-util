package okjava.util.e;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 10/3/2015
 * 01:33.
 */
@FunctionalInterface
public interface EFunction<T, R, E extends Exception>{

    static <T, R> EFunction<T, R, RuntimeException> delegate(Function<T, R> function) {
        requireNonNull(function);
        return function::apply;
    }

    static <T, R> Function<T, R> delegate(EFunction<T, R,RuntimeException> function) {
        requireNonNull(function);
        return function::apply;
    }

    R apply(T t) throws E;
}
