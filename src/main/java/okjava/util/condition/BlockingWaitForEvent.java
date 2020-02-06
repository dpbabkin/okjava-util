package okjava.util.condition;

import okjava.util.blockandwait.supplier.WaitTimeSupplier;
import okjava.util.blockandwait.supplier.WaitTimeSupplierFactory;
import okjava.util.condition.waiter.Waiter;
import okjava.util.condition.waiter.WaiterProviderImpl;
import okjava.util.condition.waiter.WaiterProviderUpdatable;
import okjava.util.poller.Updatable;

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

    private static BlockingWaitForEvent createNative(WaitTimeSupplier waitTimeSupplier) {
        WaiterProviderUpdatable waiterProviderUpdatable = WaiterProviderImpl.create(waitTimeSupplier);
        return new BlockingWaitForEvent(waiterProviderUpdatable);
    }

    private static final BlockingWaitForEvent BLOCKING_WAIT_FOR_EVENT_DEFAULT = createNative(WaitTimeSupplierFactory.create());

    public static BlockingWaitForEvent create() {
        return BLOCKING_WAIT_FOR_EVENT_DEFAULT;
    }

    public static BlockingWaitForEvent createWithPoll(long pollInterval) {
        return createNative(WaitTimeSupplierFactory.createWithPoll(pollInterval));
    }

    private static final BlockingWaitForEvent BLOCKING_WAIT_FOR_EVENT_WITH_DEFAULT_POLL = createNative(WaitTimeSupplierFactory.createWithDefaultPoll());

    public static BlockingWaitForEvent createWithDefaultPoll() {
        return BLOCKING_WAIT_FOR_EVENT_WITH_DEFAULT_POLL;
    }

    @Override
    public void onUpdate() {
        waiterProviderUpdatable.onUpdate();
    }

    private static Object NOT_NULL_OBJECT = new Object();

    public ResultWaiter waiter(Supplier<Boolean> isEventHappened) {
        Supplier<Object> adapterSupplier = () -> isEventHappened.get() ? NOT_NULL_OBJECT : null;
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
        public Result await(long time) throws InterruptedException {
            return createResult(delegate.await(time));
        }

        private Result createResult(Object object) {
            assert object == null || object == NOT_NULL_OBJECT;
            return ResultImpl.result(object != null);
        }
    }
}
