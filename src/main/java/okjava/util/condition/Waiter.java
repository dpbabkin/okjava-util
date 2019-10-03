package okjava.util.condition;

import java.util.concurrent.TimeUnit;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/6/2019
 * 19:39.
 */
public interface Waiter {

    void abort();

    Result await_() throws InterruptedException;

    Result await_(long time, TimeUnit timeUnit) throws InterruptedException;

    default Result second_() throws InterruptedException {
        return seconds_(1L);
    }

    default Result seconds_(long seconds) throws InterruptedException {
        return await_(seconds, TimeUnit.SECONDS);
    }

    default Result minute_() throws InterruptedException {
        return minutes_(1);
    }

    default Result minutes_(long value) throws InterruptedException {
        return seconds_(TimeUnit.MINUTES.toSeconds(value));
    }
}
