package okjava.util.condition.waiter;

import okjava.util.blockandwait.Constants;
import okjava.util.blockandwait.supplier.WaitTimeSupplier;
import okjava.util.blockandwait.core.BlockAndWaitGeneralImpl;
import okjava.util.blockandwait.core.BlockAndWaitGeneralUpdatable;
import okjava.util.poller.Updatable;

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
    private final WaitTimeSupplier waitTimeSupplier;

    private WaiterProviderImpl(WaitTimeSupplier waitTimeSupplier) {
        this.waitTimeSupplier = notNull(waitTimeSupplier);
    }

    public static WaiterProviderUpdatable create(WaitTimeSupplier waitTimeSupplier) {
        return new WaiterProviderImpl(waitTimeSupplier);
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
        public V await(long time) throws InterruptedException {
            LongSupplier longSupplier = () -> {
                if (cancelled) {
                    return Constants.NO_NEED_TO_WAIT;
                }
                return waitTimeSupplier.timed(time).getAsLong();
            };
            return blockAndWaitUpdatable.await(isEventHappened, longSupplier);
        }
    }
}
