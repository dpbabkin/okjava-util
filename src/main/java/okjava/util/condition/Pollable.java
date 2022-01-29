package okjava.util.condition;

import static okjava.util.blockandwait.Constants.DEFAULT_POLL_INTERVAL;
import static okjava.util.blockandwait.Constants.WAIT_FOREVER;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 28.01.2022 - 21:09.
 */
public interface Pollable<V> {
    V withPoll(long pollInterval);

    default V withoutPoll() {
        return withPoll(WAIT_FOREVER);
    }

    default V withoutDefaultPoll() {
        return withPoll(DEFAULT_POLL_INTERVAL);
    }
}
