package okjava.util.poller.poller;

import okjava.util.blockandwait.BlockAndWaitUpdatable;
import okjava.util.blockandwait.legacy.BlockAndWaitFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/20/2017
 * 17:41.
 */
public class PollerWithSupplierImpl<V> implements PollerWithSupplier<V>{
    //this is good class but I do not see application for that.

    private final static Logger LOGGER = LoggerFactory.getLogger(Poller.class);

    private final BlockAndWaitUpdatable blockAndWait = BlockAndWaitFactory.create();

    private final Supplier<V> supplier;

    private PollerWithSupplierImpl(Supplier<V> supplier) {
        this.supplier = notNull(supplier);
    }

    public static <V> PollerWithSupplier<V> create(Supplier<V> supplier) {
        return new PollerWithSupplierImpl<>(supplier);
    }

    @Override
    public void onUpdate() {
        LOGGER.trace("updated with " + get());
        blockAndWait.onUpdate();
    }
    @Override
    public V get() {
        return supplier.get();
    }

    @Override
    public V poll(Predicate<V> tester) throws InterruptedException {
        V value = blockAndWait.await(createSupplierAdapter(tester));
        assert value != null;
        return value;
    }

    @Override
    public Optional<V> poll(Predicate<V> tester, long time, TimeUnit timeUnit) throws InterruptedException {
        return Optional.ofNullable(blockAndWait.await(createSupplierAdapter(tester), time, timeUnit));
    }

    private Supplier<V> createSupplierAdapter(Predicate<V> predicate) {
        Supplier<V> supplierAdapter = () -> {
            V value = PollerWithSupplierImpl.this.get();
            if (predicate.test(value)) {
                return value;
            }
            return null;
        };
        return supplierAdapter;
    }
}
