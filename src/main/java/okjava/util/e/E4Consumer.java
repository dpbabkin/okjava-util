package okjava.util.e;

import java.util.function.Consumer;

import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 10/3/2015
 * 01:33.
 */
@FunctionalInterface
public interface E4Consumer<T, E1 extends Exception, E2 extends Exception, E3 extends Exception, E4 extends Exception> {

    static <T> E4Consumer<T, RuntimeException, RuntimeException, RuntimeException, RuntimeException> delegate(Consumer<T> consumer) {
        assert notNull(consumer) != null;
        return consumer::accept;
    }

    static <T, E extends Exception> E4Consumer<T, E, RuntimeException, RuntimeException, RuntimeException> delegate(EConsumer<T, E> consumer) {
        assert notNull(consumer) != null;
        return consumer::accept;
    }

    void accept(T a) throws E1, E2, E3, E4;
}
