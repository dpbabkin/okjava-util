package okjava.util.condition;

import okjava.util.blockandwait.WaitTimeSupplierFactory;
import okjava.util.blockandwait.general.BlockAndWaitGeneralImpl;
import okjava.util.blockandwait.general.BlockAndWaitGeneralUpdatable;
import okjava.util.poller.Updatable;

import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/5/2019
 * 09:38.
 */
public final class WaiterProviderImpl implements Updatable, WaiterProviderUpdatable {
    private final BlockAndWaitGeneralUpdatable blockAndWaitUpdatable = BlockAndWaitGeneralImpl.create();
    private final WaitTimeSupplierFactory waitTimeSupplierFactory;

    private WaiterProviderImpl(WaitTimeSupplierFactory waitTimeSupplierFactory) {
        this.waitTimeSupplierFactory = notNull(waitTimeSupplierFactory);
    }

    public static WaiterProviderUpdatable create(WaitTimeSupplierFactory waitTimeSupplierFactory) {
        return new WaiterProviderImpl(waitTimeSupplierFactory);
    }

    @Override
    public void onUpdate() {
        this.blockAndWaitUpdatable.onUpdate();
    }

    public <V> Waiter<V> waiter(Supplier<V> isEventHappened) {
        return new WaiterProviderImpl.WaiterImpl<>(isEventHappened);
    }

    private final class WaiterImpl<V> implements Waiter<V> {
        private final Supplier<V> isEventHappened;
        private volatile boolean cancelled = false;

        private WaiterImpl(Supplier<V> isEventHappened) {
            notNull(isEventHappened);
            this.isEventHappened = isEventHappened;
        }

        @Override
        public void cancel() {
            cancelled = true;
            onUpdate();
        }

        @Override
        public V await() throws InterruptedException {
            LongSupplier longSupplier = () -> {
                if (cancelled) {
                    return BlockAndWaitGeneralImpl.NO_NEED_TO_WAIT;
                }
                return waitTimeSupplierFactory.infinite().getAsLong();
            };
            return blockAndWaitUpdatable.await(isEventHappened, longSupplier);
        }

        @Override
        public V await(long time, TimeUnit timeUnit) throws InterruptedException {
            LongSupplier longSupplier = () -> {
                if (cancelled) {
                    return BlockAndWaitGeneralImpl.NO_NEED_TO_WAIT;
                }
                return waitTimeSupplierFactory.timed(time, timeUnit).getAsLong();
            };
            return blockAndWaitUpdatable.await(isEventHappened, longSupplier);
        }
    }
}
