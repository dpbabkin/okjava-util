package okjava.util.condition;

import okjava.util.blockandwait.PollerWaitTimeSupplierFactory;
import okjava.util.blockandwait.SimpleWaitTimeSupplierFactory;
import okjava.util.blockandwait.WaitTimeSupplierFactory;
import okjava.util.poller.Updatable;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/5/2019
 * 09:38.
 */
public final class BlockingWaitForEvent implements Updatable {

    private final WaiterProviderUpdatable waiterProviderUpdatable;

    private BlockingWaitForEvent(WaiterProviderUpdatable blockAndWaitUpdatable) {
        this.waiterProviderUpdatable = notNull(blockAndWaitUpdatable);
    }

    private static BlockingWaitForEvent createNative(WaitTimeSupplierFactory waitTimeSupplierFactory) {
        WaiterProviderUpdatable waiterProviderUpdatable = WaiterProviderImpl.create(waitTimeSupplierFactory);
        return new BlockingWaitForEvent(waiterProviderUpdatable);
    }

    public static BlockingWaitForEvent createWithPoll(long pollInterval) {
        return createNative(PollerWaitTimeSupplierFactory.create(pollInterval));
    }

    public static BlockingWaitForEvent create() {
        return createNative(SimpleWaitTimeSupplierFactory.create());
    }

    public static BlockingWaitForEvent createWithPoll() {
        return createNative(PollerWaitTimeSupplierFactory.createDefault());
    }

    @Override
    public void onUpdate() {
        waiterProviderUpdatable.onUpdate();
    }

    private static Object MOCK = new Object();

    public ResultWaiter waiter(Supplier<Boolean> isEventHappened) {
        Supplier<Object> adapterSupplier = () -> isEventHappened.get() ? MOCK : null;
        Waiter<Object> delegate = waiterProviderUpdatable.waiter(adapterSupplier);
        return new ResultWaiterDelegate(delegate);
    }

    private static final class ResultWaiterDelegate implements ResultWaiter {
        private final Waiter<Object> delegate;

        private ResultWaiterDelegate(Waiter<Object> delegate) {
            this.delegate = notNull(delegate);
        }

        @Override
        public void cancel() {
            delegate.cancel();
        }

        @Override
        public Result await() throws InterruptedException {
            return ResultImpl.result(delegate.await() != null);
        }

        @Override
        public Result await(long time, TimeUnit timeUnit) throws InterruptedException {
            return ResultImpl.result(delegate.await(time, timeUnit) != null);
        }
    }
}
