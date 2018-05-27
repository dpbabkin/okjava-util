package okjava.util.function;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/27/2018
 * 21:55.
 */
public interface FunctionAndBiFunction<T, U, R> extends Function<T, R>, BiFunction<T, U, R> {
    @Override
    default <V> FunctionAndBiFunction<T, U, V> andThen(Function<? super R, ? extends V> after) {
        return new FunctionAndBiFunction<T, U, V>() {
            @Override
            public V apply(final T t, final U u) {
                return after.apply(FunctionAndBiFunction.this.apply(t, u));
            }

            @Override
            public V apply(final T t) {
                return after.apply(FunctionAndBiFunction.this.apply(t));
            }
        };
    }
}
