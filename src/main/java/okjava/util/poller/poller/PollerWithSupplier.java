package okjava.util.poller.poller;

import okjava.util.poller.Updatable;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/20/2017
 * 17:41.
 */
public interface PollerWithSupplier<V> extends Poller<V>, Updatable {
}
