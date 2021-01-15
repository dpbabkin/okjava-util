package okjava.util.blockandwait.legacy;

import okjava.util.blockandwait.BlockAndWaitUpdatable;
import okjava.util.blockandwait.Constants;
import okjava.util.blockandwait.core.BlockAndWaitGeneralImpl;
import okjava.util.blockandwait.core.BlockAndWaitGeneralUpdatable;

import java.util.function.LongSupplier;
import java.util.function.Supplier;

import static java.lang.Math.max;

abstract class AbstractBlockAndWait implements BlockAndWaitUpdatable {
    private final BlockAndWaitGeneralUpdatable blockAndWaitUpdatable = BlockAndWaitGeneralImpl.create();

    private volatile boolean abort = false;

    AbstractBlockAndWait() {
    }

    public void onUpdate() {
        blockAndWaitUpdatable.onUpdate();
    }

    @Override
    public <V> V await(Supplier<V> isEventHappened, long time) throws InterruptedException {
        if(time == Constants.WAIT_FOREVER){
            return doAwait(isEventHappened, getInfiniteWaitSupplier());
        }
        return doAwait(isEventHappened, createWaitTimeSupplier(time));
    }

    private <V> V doAwait(Supplier<V> isEventHappened, LongSupplier needToWaitProvider) throws InterruptedException {
        return blockAndWaitUpdatable.await(isEventHappened, wrapAbort(needToWaitProvider));
    }

    private LongSupplier wrapAbort(LongSupplier delegate) {
        return () -> {
            if (abort) {
                return Constants.NO_NEED_TO_WAIT;
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
