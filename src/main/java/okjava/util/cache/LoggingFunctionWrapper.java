package okjava.util.cache;

import okjava.util.string.ToStringBuffer;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;

import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 15.01.2021 19:22
 */
final class LoggingFunctionWrapper<K, V> implements com.google.common.base.Function<K, V> {

    private final Function<K, V> delegate;
    private final Consumer<String> logger;

    private LoggingFunctionWrapper(Function<K, V> delegate, Consumer<String> logger) {

        this.delegate = notNull(delegate);
        this.logger = notNull(logger);

    }

    static <K, V> LoggingFunctionWrapper<K, V> create(Function<K, V> delegate, Consumer<String> logger) {
        return new LoggingFunctionWrapper<>(delegate, logger);
    }

    @Nullable
    @Override
    public V apply(@Nullable K key) {
        V value = delegate.apply(key);
        ToStringBuffer.string("add to cache")
                .add("key", key)
                .add("value", value)
                .toConsumer(logger);
        return value;
    }
}
