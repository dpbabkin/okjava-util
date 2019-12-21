package okjava.util.blockandwait.legacy;

import okjava.util.blockandwait.BlockAndWaitUpdatable;
import okjava.util.blockandwait.general.BlockAndWaitGeneralImpl;
import okjava.util.blockandwait.general.BlockAndWaitGeneralUpdatable;

import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

import static java.lang.Math.max;

abstract class BaseBlockAndWait implements BlockAndWaitUpdatable {
    private final BlockAndWaitGeneralUpdatable blockAndWaitUpdatable = BlockAndWaitGeneralImpl.create();
    static private final LongSupplier ABORT_WAIT_TIME_SUPPLIER = () -> BlockAndWaitGeneralImpl.NO_NEED_TO_WAIT;

    private volatile boolean abort = false;

    BaseBlockAndWait() {
    }

    public void onUpdate() {
        blockAndWaitUpdatable.onUpdate();
    }

    @Override
    public <V> V await(Supplier<V> isEventHappened) throws InterruptedException {
        return doAwait(isEventHappened, getInfiniteWaitSupplier());
    }

    @Override
    public <V> V await(Supplier<V> isEventHappened, long time, TimeUnit timeUnit) throws InterruptedException {
        return doAwait(isEventHappened, createWaitTimeSupplier(timeUnit.toMillis(time)));
    }

    private <V> V doAwait(Supplier<V> isEventHappened, LongSupplier needToWaitProvider) throws InterruptedException {
        return blockAndWaitUpdatable.await(isEventHappened, wrapAbort(needToWaitProvider));
    }

    private LongSupplier wrapAbort(LongSupplier delegate) {
        return () -> {
            if (abort) {
                return BlockAndWaitGeneralImpl.NO_NEED_TO_WAIT;
            }
            return delegate.getAsLong();
        };
    }

    protected abstract LongSupplier getInfiniteWaitSupplier();

    protected LongSupplier createWaitTimeSupplier(long requestedWaitTime) {
        final long startTime = System.currentTimeMillis();
        return () -> getElapsedTime(startTime, requestedWaitTime);
    }

    protected long getElapsedTime(long startTime, long requestedWaitTime) {
        final long timeElapsed = (System.currentTimeMillis() - startTime);
        final long needToWait = requestedWaitTime - timeElapsed;
        // returns value in [0, pollInterval]
        // zero means run out of time.
        return max(0, needToWait);
    }

}
