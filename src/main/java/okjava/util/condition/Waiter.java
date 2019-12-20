package okjava.util.condition;

import java.util.concurrent.TimeUnit;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/6/2019
 * 19:39.
 */
public interface Waiter {

    void abort();

    Result await() throws InterruptedException;

    Result await(long time, TimeUnit timeUnit) throws InterruptedException;

    default Result second() throws InterruptedException {
        return seconds(1L);
    }

    default Result seconds(long seconds) throws InterruptedException {
        return await(seconds, TimeUnit.SECONDS);
    }

    default Result minute() throws InterruptedException {
        return minutes(1L);
    }

    default Result minutes(long value) throws InterruptedException {
        return seconds(TimeUnit.MINUTES.toSeconds(value));
    }
}
