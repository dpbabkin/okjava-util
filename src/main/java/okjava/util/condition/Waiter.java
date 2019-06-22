package okjava.util.condition;

import java.util.concurrent.TimeUnit;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/6/2019
 * 19:39.
 */
public interface Waiter {

    void abort();

    boolean await() throws InterruptedException;

    boolean await(long time, TimeUnit timeUnit) throws InterruptedException;

    default boolean second() throws InterruptedException {
        return await(1, TimeUnit.SECONDS);
    }

    default boolean minute() throws InterruptedException {
        return await(1, TimeUnit.MINUTES);
    }
}
