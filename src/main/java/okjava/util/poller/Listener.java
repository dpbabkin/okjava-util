package okjava.util.poller;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 3/31/2018
 * 19:34.
 */
public interface Listener<V> extends Consumer<Supplier<V>> {

}
