package okjava.util.e;

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 7/3/2016
 * 22:17.
 */
@FunctionalInterface
public interface EPredicate<T, E extends Exception> {

    static <T> EPredicate<T, RuntimeException> delegate(Predicate<T> predicate) {
        requireNonNull(predicate);
        return predicate::test;
    }

    boolean test(T t) throws E;
}
