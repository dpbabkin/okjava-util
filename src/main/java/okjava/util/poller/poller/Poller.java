package okjava.util.poller.poller;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/20/2017
 * 17:41.
 */
public interface Poller<V> extends Supplier<V> {

    V poll(Predicate<V> tester) throws InterruptedException;

    Optional<V> poll(Predicate<V> tester, long time) throws InterruptedException;

    @Deprecated //use with caution. as old value might be set already before method starts.
    default V poll() throws InterruptedException {
        V oldValue = get();
        return poll(oldValue);
    }

    /**
     * Returns when values in the poller became is not equal to {@code oldValue}
     */
    default V poll(final V oldValue) throws InterruptedException {
        return poll(v -> !oldValue.equals(v));
    }

    default Optional<V> poll(long time, TimeUnit timeUnit) throws InterruptedException {
        return pollWait(timeUnit.toMillis(time));
    }

    /**
     * After call immediately takes current value.
     * Returns when value changes.
     */
    default Optional<V> pollWait(long time) throws InterruptedException {
        V oldValue = get();
        return poll(oldValue, time);
    }

    /**
     * Returns when values in the poller became is not equal to {@code oldValue}
     * Returns empty {@code Optional} if after time elapsed condition mentioned is not met.
     */
    default Optional<V> poll(final V oldValue, long time, TimeUnit timeUnit) throws InterruptedException {
        return poll(oldValue, timeUnit.toMillis(time));
    }

    /**
     * Returns when values in the poller became is not equal to {@code oldValue}
     * Returns empty {@code Optional} if after time elapsed condition mentioned is not met.
     */
    default Optional<V> poll(final V oldValue, long time) throws InterruptedException {
        return poll(v -> !oldValue.equals(v), time);
    }

    /**
     * Returns when values in the poller became is not equal to {@code oldValue}
     * Returns empty {@code Optional} if after time elapsed @{code Predicate} on value is not true.
     */
    default Optional<V> poll(Predicate<V> tester, long time, TimeUnit timeUnit) throws InterruptedException {
        return poll(tester, timeUnit.toMillis(time));
    }
}
