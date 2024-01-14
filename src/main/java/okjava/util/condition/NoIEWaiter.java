package okjava.util.condition;

import okjava.util.blockandwait.Constants;

import java.util.concurrent.TimeUnit;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/6/2019
 * 19:39.
 */
public interface NoIEWaiter<V> extends Waiter<V> {

    Waiter<V> toWaiter();

    V await(long time);

    default V await() {
        return await(Constants.WAIT_FOREVER);
    }

    default V await(long time, TimeUnit timeUnit) {
        return await(timeUnit.toMillis(time));
    }

    default V second() {
        return seconds(1L);
    }

    default V fiveSeconds() {
        return seconds(5L);
    }

    default V tenSeconds() {
        return seconds(10L);
    }

    default V seconds(long seconds) {
        return await(seconds, TimeUnit.SECONDS);
    }

    default V minute() {
        return minutes(1L);
    }

    default V minutes(long value) {
        return seconds(TimeUnit.MINUTES.toSeconds(value));
    }
}
