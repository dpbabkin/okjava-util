package okjava.util;

import static okjava.util.check.Never.neverNeverCalled;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;


/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/1/2016
 * 21:39.
 */
@Utility
public enum ConsumerUtils {
    ;

    ConsumerUtils(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static <V> Consumer<V> wrapToString(Consumer<V> consumer, Supplier<String> toStringSupplier) {
        return new Consumer<V>() {


            @Override
            public void accept(V v) {
                consumer.accept(v);
            }

            @Override
            public String toString() {
                return toStringSupplier.get();
            }
        };
    }


    public static <V1, V2> Consumer<V2> map(Consumer<V1> consumer, Function<V2, V1> mapper) {
        return (v) -> consumer.accept(mapper.apply(v));
    }
}