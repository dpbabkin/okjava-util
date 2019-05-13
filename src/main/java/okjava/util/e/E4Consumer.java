package okjava.util.e;

import static java.util.Objects.requireNonNull;

import java.util.function.Consumer;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 10/3/2015
 * 01:33.
 */
@FunctionalInterface
public interface E4Consumer<T, E1 extends Exception, E2 extends Exception, E3 extends Exception, E4 extends Exception> {

    static <T> E4Consumer<T, RuntimeException, RuntimeException, RuntimeException, RuntimeException> delegate(Consumer<T> consumer) {
        requireNonNull(consumer);
        return consumer::accept;
    }

    void accept(T a) throws E1, E2, E3, E4;
}
