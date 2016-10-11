package okjava.util.e;

import static java.util.Objects.requireNonNull;

import java.util.function.Supplier;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         2/15/2015
 *         23:58.
 */
@FunctionalInterface
public interface ESupplier<V, E extends Exception> {

    static <T> ESupplier<T, RuntimeException> delegate(Supplier<T> supplier) {
        requireNonNull(supplier);
        return supplier::get;
    }

    V get() throws E;
}
