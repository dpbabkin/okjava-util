package okjava.util.poller.poller;

import okjava.util.condition.WaiterProviderImpl;
import okjava.util.condition.WaiterProviderUpdatable;
import okjava.util.condition.Waiter;
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
public class PollerWithSupplierImpl_NEW<V> implements PollerWithSupplier<V>, SuperPuller<V> {
    //this is good class but I do not see application for that.

    private final static Logger LOGGER = LoggerFactory.getLogger(Poller.class);

    private final WaiterProviderUpdatable blockAndWait = WaiterProviderImpl.create(null);


    private final Supplier<V> supplier;

    private PollerWithSupplierImpl_NEW(Supplier<V> supplier) {
        this.supplier = notNull(supplier);
    }

    public static <V> PollerWithSupplier<V> create(Supplier<V> supplier) {
        return new PollerWithSupplierImpl_NEW<>(supplier);
    }

    @Override
    public void onUpdate() {
        LOGGER.trace("updated with " + get());
        blockAndWait.onUpdate();
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("not implemented //code generation template");
    }

    @Override
    public V get() {
        return supplier.get();
    }


    @Override
    public V poll(Predicate<V> tester) throws InterruptedException {
        Waiter<V> waiterUniversal = createWaiter(tester);
        V value = waiterUniversal.await();
        assert value != null;
        return value;
    }

    @Override
    public Optional<V> poll(Predicate<V> tester, long time, TimeUnit timeUnit) throws InterruptedException {
        Waiter<V> waiterUniversal = createWaiter(tester);
        return Optional.ofNullable(waiterUniversal.await(time, timeUnit));
    }

    private Waiter<V> createWaiter(Predicate<V> tester){
        return blockAndWait.waiter(createSupplierAdapter(tester));
    }

    private Supplier<V> createSupplierAdapter(Predicate<V> predicate) {
        Supplier<V> supplierAdapter = () -> {
            V value = PollerWithSupplierImpl_NEW.this.get();
            if (predicate.test(value)) {
                return value;
            }
            return null;
        };
        return supplierAdapter;
    }


}
