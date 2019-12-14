package okjava.util.poller;

import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/20/2017
 * 17:41.
 */
public interface Poller<V> extends Supplier<V> {

    default V poll() throws InterruptedException {
        V oldValue = get();
        return poll(oldValue);
    }

    default V poll(final V oldValue) throws InterruptedException {
        return poll(v -> !oldValue.equals(v));
    }

    V poll(Predicate<V> tester) throws InterruptedException;
}
