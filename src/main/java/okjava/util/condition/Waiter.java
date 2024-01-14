package okjava.util.condition;

import okjava.util.blockandwait.Constants;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/6/2019
 * 19:39.
 */
public interface Waiter<V> extends Updatable, Cancellable, Pollable<Waiter<V>> {

    default <V1> Waiter<V1> map(Function<V, V1> mapper) {
        return WaiterDelegateMapper.create(this, mapper);
    }

    default NoIEWaiter<V> toNoIEWaiter() {
        return NoIEWaiterDelegate.create(this);
    }

    V await(long time) throws InterruptedException;

    default V await() throws InterruptedException {
        return await(Constants.WAIT_FOREVER);
    }

    default V await(long time, TimeUnit timeUnit) throws InterruptedException {
        return await(timeUnit.toMillis(time));
    }

    default V second() throws InterruptedException {
        return seconds(1L);
    }

    default V fiveSeconds() throws InterruptedException {
        return seconds(5L);
    }

    default V tenSeconds() throws InterruptedException {
        return seconds(10L);
    }

    default V seconds(long seconds) throws InterruptedException {
        return await(seconds, TimeUnit.SECONDS);
    }

    default V minute() throws InterruptedException {
        return minutes(1L);
    }

    default V minutes(long value) throws InterruptedException {
        return seconds(TimeUnit.MINUTES.toSeconds(value));
    }
}
