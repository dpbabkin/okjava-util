package okjava.util.poller;

import okjava.util.thread.ExecutorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Predicate;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/20/2017
 * 17:41.
 */
public class PollerWithVaueImpl<V> implements PollerWithValue<V> {

    private final static Logger LOGGER = LoggerFactory.getLogger(Poller.class);

    private volatile V value;

    private final PollerWithSupplier<V> genericPollerDelegate = PollerWithSupplierImpl.create(() -> value);
    private final Runnable updateGenericPollerDelegate = genericPollerDelegate::onUpdate;

    private PollerWithVaueImpl(V value) {
        this.value = value;
    }

    public static <V> PollerWithValue<V> create(V value) {
        return new PollerWithVaueImpl<>(value);
    }

    @Override
    public V get() {
        return value;
    }

    @Override
    public void accept(V value) {
        this.value = value;
        ExecutorFactory.getInstance().getExecutor().execute(updateGenericPollerDelegate);
    }

    @Override
    public V poll(Predicate<V> tester) throws InterruptedException {
        return genericPollerDelegate.poll(tester);
    }
}
