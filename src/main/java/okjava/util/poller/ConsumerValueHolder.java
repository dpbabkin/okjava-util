package okjava.util.poller;

import okjava.util.Two;

import java.util.function.Function;

public interface ConsumerValueHolder<V> extends ValueHolder<V>, Function<V, V> {
    Two<V,V> mutate(Function<V, V> mutator);
}
