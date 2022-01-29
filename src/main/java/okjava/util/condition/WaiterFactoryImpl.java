package okjava.util.condition;

import okjava.util.blockandwait.BlockAndWait;
import okjava.util.blockandwait.BlockAndWaits;
import okjava.util.check.MathCheck;

import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static okjava.util.blockandwait.Constants.WAIT_FOREVER;
import static okjava.util.condition.ResultWaiterBooleanAdapter.NOT_NULL_OBJECT;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/5/2019
 * 09:38.
 */
final class WaiterFactoryImpl implements WaiterFactory {
    private final BlockAndWait blockAndWait = BlockAndWaits.create();
    private final long pollInterval;

    private WaiterFactoryImpl(long pollInterval) {
        this.pollInterval = MathCheck.positive(pollInterval);
    }

    private static WaiterFactory create(long pollInterval) {
        return new WaiterFactoryImpl(pollInterval);
    }

    static WaiterFactory create() {
        return create(WAIT_FOREVER);
    }

    @Override
    public Updatable getUpdatable() {
        return blockAndWait;
    }

    @Override
    public Cancellable getCancellable() {
        return blockAndWait;
    }

    public <V> Waiter<V> waiter(Supplier<V> isEventHappened) {
        return WaiterImpl.create(blockAndWait, isEventHappened).withPoll(pollInterval);
    }

    @Override
    public Waiter<Result> waiterBoolean(BooleanSupplier isEventHappened) {
        Supplier<Object> delegate = () -> isEventHappened.getAsBoolean() ? NOT_NULL_OBJECT : null;
        return ResultWaiterBooleanAdapter.create(waiter(delegate));
    }

    @Override
    public WaiterFactory withPoll(long pollInterval) {
        return create(pollInterval);
    }

}
