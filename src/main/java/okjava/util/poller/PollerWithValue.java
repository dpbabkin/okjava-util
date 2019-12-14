package okjava.util.poller;

import java.util.function.Consumer;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/20/2017
 * 17:41.
 */
public interface PollerWithValue<V> extends Poller<V>, Consumer<V> {
}
