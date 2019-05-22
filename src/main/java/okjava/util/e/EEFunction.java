package okjava.util.e;

import static java.util.Objects.requireNonNull;

import java.util.function.Function;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 10/3/2015
 * 01:33.
 */
@FunctionalInterface
public interface EEFunction<T, R, E1 extends Exception, E2 extends Exception> {

    static <T, R> EEFunction<T, R, RuntimeException, RuntimeException> delegate(Function<T, R> function) {
        requireNonNull(function);
        return function::apply;
    }

    R apply(T t) throws E1, E2;
}
