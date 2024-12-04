package okjava.util.e;

import java.util.function.BiConsumer;

import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 10/3/2015
 * 01:33.
 */
@FunctionalInterface
public interface E4BiConsumer<T1, T2, E1 extends Exception, E2 extends Exception, E3 extends Exception, E4 extends Exception> {

    static <T1, T2> E4BiConsumer<T1, T2, RuntimeException, RuntimeException, RuntimeException, RuntimeException> delegate(BiConsumer<T1, T2> consumer) {
        assert notNull(consumer) != null;
        return consumer::accept;
    }

    static <T1, T2, E extends Exception> E4BiConsumer<T1, T2, E, RuntimeException, RuntimeException, RuntimeException> delegate(
            EBiConsumer<T1, T2, E> consumer) {
        assert notNull(consumer) != null;
        return consumer::accept;
    }

    void accept(T1 t1, T2 t2) throws E1, E2, E3, E4;
}
