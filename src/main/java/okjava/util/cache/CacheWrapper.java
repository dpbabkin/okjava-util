package okjava.util.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import okjava.util.logger.LoggerUtils;
import okjava.util.string.ToStringBuffer;
import org.slf4j.Logger;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static okjava.util.blockandwait.Constants.WAIT_FOREVER;

public final class CacheWrapper<K, V> implements Function<K, V> {
    private static final Logger LOGGER = LoggerUtils.createLogger(CacheWrapper.class);
    private final LoadingCache<K, V> cache;

    private CacheWrapper(Function<K, V> function, long time) {

        CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder();
        if (time != WAIT_FOREVER) {
            builder.expireAfterAccess(time, TimeUnit.MILLISECONDS);
        }

        this.cache = builder
                .removalListener(getRemovalListener())
                .build(CacheLoader.from(LoggingFunctionWrapper.create(function, LOGGER::debug)));
    }

    private static final RemovalListener<?, ?> REMOVAL_LISTENER = new RemovalListenerImpl<>();

    @SuppressWarnings("unchecked")
    private static <K, V> RemovalListener<K, V> getRemovalListener() {
        return (RemovalListener<K, V>) REMOVAL_LISTENER;
    }

    private final static class RemovalListenerImpl<K, V> implements RemovalListener<K, V> {

        @Override
        public void onRemoval(RemovalNotification<K, V> notification) {
            LOGGER.debug(ToStringBuffer.string("remove from cache")
                    .add("key", notification.getKey())
                    .add("value", notification.getValue())
                    .add("RemovalCause", notification.getCause())
                    .toString());
        }
    }

    @Override
    public V apply(K k) {
        return cache.getUnchecked(k);
    }


    public static <K, V> Function<K, V> hour(Function<K, V> function) {
        return hours(function, 1);
    }

    protected static <K, V> Function<K, V> hours(Function<K, V> function, int hours) {
        return from(function, hours, TimeUnit.HOURS);
    }


    public static <K, V> Function<K, V> minute(Function<K, V> function) {
        return minutes(function, 1);
    }

    protected static <K, V> Function<K, V> minutes(Function<K, V> function, int minutes) {
        return from(function, minutes, TimeUnit.MINUTES);
    }

    public static <K, V> Function<K, V> from(Function<K, V> function, long time, TimeUnit timeUnit) {
        return new CacheWrapper<>(function, timeUnit.toMillis(time));
    }

    public static <K, V> Function<K, V> from(Function<K, V> function, long time) {
        return new CacheWrapper<>(function, time);
    }

    public static <K, V> Function<K, V> from(Function<K, V> function) {
        return from(function, WAIT_FOREVER);
    }
}
