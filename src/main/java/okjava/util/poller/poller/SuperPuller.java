package okjava.util.poller.poller;

import okjava.util.poller.Updatable;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface SuperPuller<V> extends Supplier<V>, Updatable, Poller<V> {

    default Optional<V> poll(long time, TimeUnit timeUnit) throws InterruptedException {
        V oldValue = get();
        return poll(oldValue, time, timeUnit);
    }

    default Optional<V> poll(final V oldValue, long time, TimeUnit timeUnit) throws InterruptedException {
        return poll(v -> !oldValue.equals(v), time, timeUnit);
    }

    Optional<V> poll(Predicate<V> tester, long time, TimeUnit timeUnit) throws InterruptedException;
}