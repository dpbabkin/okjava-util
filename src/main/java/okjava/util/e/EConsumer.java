package okjava.util.e;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 10/3/2015
 * 01:33.
 */
@FunctionalInterface
public interface EConsumer<T, E extends Exception> {

    static <T,E extends Exception> EConsumer<T, E> delegate(Consumer<T> consumer) {
        requireNonNull(consumer);
        return consumer::accept;
    }

    static <T, E extends Exception> EConsumer<T, E> wrapToString(EConsumer<T, E> consumer, Supplier<String> toStringSupplier) {
        return new EConsumer<T, E>() {


            @Override
            public void accept(T t) throws E {
                consumer.accept(t);
            }

            @Override
            public String toString() {
                return toStringSupplier.get();
            }
        };
    }


    void accept(T a) throws E;
}
