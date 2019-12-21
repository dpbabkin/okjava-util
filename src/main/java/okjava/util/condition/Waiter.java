package okjava.util.condition;

import java.util.concurrent.TimeUnit;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/6/2019
 * 19:39.
 */
public interface Waiter<V> {

    void cancel();

    V await() throws InterruptedException;

    V await(long time, TimeUnit timeUnit) throws InterruptedException;

    default V second() throws InterruptedException {
        return seconds(1L);
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
