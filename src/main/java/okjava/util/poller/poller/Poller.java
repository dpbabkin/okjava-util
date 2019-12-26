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

    default V poll() throws InterruptedException {
        V oldValue = get();
        return poll(oldValue);
    }

    default V poll(final V oldValue) throws InterruptedException {
        return poll(v -> !oldValue.equals(v));
    }

    default Optional<V> poll(long time, TimeUnit timeUnit) throws InterruptedException {
        return poll(timeUnit.toMillis(time));
    }

    default Optional<V> poll(long time) throws InterruptedException {
        V oldValue = get();
        return poll(oldValue, time);
    }

    default Optional<V> poll(final V oldValue, long time, TimeUnit timeUnit) throws InterruptedException {
        return poll(oldValue, timeUnit.toMillis(time));
    }

    default Optional<V> poll(final V oldValue, long time) throws InterruptedException {
        return poll(v -> !oldValue.equals(v), time);
    }

    default Optional<V> poll(Predicate<V> tester, long time, TimeUnit timeUnit) throws InterruptedException {
        return poll(tester, timeUnit.toMillis(time));
    }
}
