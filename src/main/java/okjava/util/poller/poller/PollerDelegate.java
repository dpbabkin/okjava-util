package okjava.util.poller.poller;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/20/2017
 * 17:41.
 */
class PollerDelegate<V> implements Poller<V> {
    private final Poller<V> delegate;

    PollerDelegate(Poller<V> delegate) {
        this.delegate = delegate;
    }

    @Override
    public V poll(Predicate<V> tester) throws InterruptedException {
        return delegate.poll(tester);
    }

    @Override
    public Optional<V> poll(Predicate<V> tester, long time) throws InterruptedException {
        return delegate.poll(tester, time);
    }

    @Override
    public V get() {
        return delegate.get();
    }
}
