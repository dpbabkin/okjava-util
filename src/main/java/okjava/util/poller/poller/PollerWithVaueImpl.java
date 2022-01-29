package okjava.util.poller.poller;

import okjava.util.string.ToStringBuffer;
import okjava.util.thread.ExecutorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/20/2017
 * 17:41.
 */
public class PollerWithVaueImpl<V> implements PollerWithValue<V> {

    private final static Logger LOGGER = LoggerFactory.getLogger(Poller.class);
    private volatile V value;
    private final PollerWithSupplier<V> pollerDelegate = PollerWithSupplierImpl.create(() -> value);
    private final Runnable updatePollerDelegate = pollerDelegate::onUpdate;

    private PollerWithVaueImpl(V value) {
        this.value = value;
    }

    public static <V> PollerWithValue<V> create(V value) {
        return new PollerWithVaueImpl<>(value);
    }

    @Override
    public V get() {
        return pollerDelegate.get();
    }

    @Override
    public void accept(V value) {
        this.value = value;
        ExecutorFactory.getInstance().getExecutor().execute(updatePollerDelegate);
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(ToStringBuffer.string("poller accepted").add("value", value).toString());
        }
    }

    @Override
    public V poll(Predicate<V> tester) throws InterruptedException {
        return pollerDelegate.poll(tester);
    }

    @Override
    public Optional<V> poll(Predicate<V> tester, long time) throws InterruptedException {
        return pollerDelegate.poll(tester, time);
    }
}
