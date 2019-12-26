package okjava.util.poller.poller;

import okjava.util.blockandwait.BlockAndWaitFactory;
import okjava.util.blockandwait.BlockAndWaitUpdatable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/20/2017
 * 17:41.
 */
public class PollerWithSupplierImpl<V> implements PollerWithSupplier<V> {

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
        LOGGER.atTrace().log("poller updated with {}", get());
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
    public Optional<V> poll(Predicate<V> tester, long time) throws InterruptedException {
        return Optional.ofNullable(blockAndWait.await(createSupplierAdapter(tester), time));
    }

    /**
     * Purpose of this adapter is to abey contract of BlockAndWaitUpdatable to return null on ase of fail, or Object in case of success.
     */
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
