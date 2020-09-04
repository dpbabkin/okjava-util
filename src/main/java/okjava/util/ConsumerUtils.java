package okjava.util;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;
import okjava.util.e.EConsumer;
import okjava.util.e.EFunction;
import okjava.util.e.handler.atomic.ExceptionHandler;
import okjava.util.exception.SneakyThrower;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static okjava.util.check.Never.neverNeverCalled;


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

    public static <V1, V2, E extends Exception> EConsumer<V2, E> map(EConsumer<V1, E> eConsumer, EFunction<V2, V1, E> mapper) {
        return (v) -> eConsumer.accept(mapper.apply(v));
    }

    public static <V1, V2, E extends Exception> EConsumer<V2, E> map(Consumer<V1> eConsumer, EFunction<V2, V1, E> mapper) {
        return (v) -> eConsumer.accept(mapper.apply(v));
    }

    public static <V, E extends Exception> Consumer<V> divertExceptionFromEConsumerAndWrapToRuntime(EConsumer<V, E> eConsumer) {
        return divertExceptionFromEConsumer(eConsumer, e -> {
            throw new RuntimeException(e.getMessage(), e);
        });
    }

    @SuppressWarnings("unchecked")
    public static <V, E extends Exception> Consumer<V> divertExceptionFromEConsumer(EConsumer<V, E> eConsumer, ExceptionHandler<E> exceptionHandler) {

        Consumer<V> result = value -> {
            try {
                eConsumer.accept(value);
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                try {
                    exceptionHandler.accept((E) e);
                } catch (ClassCastException e1) {
                    //hm... not declared checked exception type thrown.
                    SneakyThrower.sneakyThrow(e);
                }
            }
        };
        return result;
    }
}
