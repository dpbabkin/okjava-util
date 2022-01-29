package okjava.util.condition;

import okjava.util.blockandwait.BlockAndWait;
import okjava.util.check.MathCheck;

import java.util.function.Supplier;

import static okjava.util.NotNull.notNull;
import static okjava.util.blockandwait.Constants.NO_NEED_TO_WAIT;
import static okjava.util.blockandwait.Constants.WAIT_FOREVER;

/**
 * Implementation of UpdatableWaiter.
 * Cancellation is implemented by changing boolean flag inside LongSupplier needToWaitProvider
 *
 * @param <V>
 */
final class WaiterImpl<V> implements Waiter<V> {
    private final Supplier<V> isEventHappened;
    private final long pollInterval;
    private final BlockAndWait blockAndWait;

    private WaiterImpl(BlockAndWait blockAndWait, Supplier<V> isEventHappened) {
        this(blockAndWait, WAIT_FOREVER, isEventHappened);
    }

    private WaiterImpl(BlockAndWait blockAndWait, long pollInterval, Supplier<V> isEventHappened) {
        this.blockAndWait = notNull(blockAndWait);
        this.isEventHappened = notNull(isEventHappened);
        this.pollInterval = notNull(pollInterval);
    }

    static <V> Waiter<V> create(BlockAndWait blockAndWait, Supplier<V> isEventHappened) {
        return new WaiterImpl<>(blockAndWait, isEventHappened);
    }

    @Override
    public void onUpdate() {
        blockAndWait.onUpdate();
    }

    @Override
    public void cancel() {
        blockAndWait.cancel();
    }

    @Override
    public Waiter<V> withPoll(long pollInterval) {
        if (pollInterval == this.pollInterval) {
            return this;
        }
        MathCheck.positive(pollInterval);
        return new WaiterImpl<>(blockAndWait, pollInterval, isEventHappened);
    }


    @Override
    public V await(long time) throws InterruptedException {
        if (pollInterval == WAIT_FOREVER) {
            return blockAndWait.await(isEventHappened, time);
        }
        final long startTime = System.currentTimeMillis();

        for (; ; ) {
            final long alreadyPassedTime = System.currentTimeMillis() - startTime;
            assert alreadyPassedTime >= 0;
            long needToWait = time - alreadyPassedTime;

            needToWait = Math.min(pollInterval, needToWait);
            needToWait = Math.max(NO_NEED_TO_WAIT, needToWait);

            V result = blockAndWait.await(isEventHappened, needToWait);

            if (result != null || needToWait == NO_NEED_TO_WAIT) {
                return result;
            }
        }
    }
}
