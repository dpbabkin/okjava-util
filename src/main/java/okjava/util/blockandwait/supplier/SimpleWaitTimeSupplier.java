package okjava.util.blockandwait.supplier;

import okjava.util.annotation.Singleton;
import okjava.util.blockandwait.Constants;

import java.util.function.LongSupplier;

import static java.lang.Math.max;
import static okjava.util.check.Once.calledOnce;

@Singleton
class SimpleWaitTimeSupplier implements WaitTimeSupplier {

    private static final LongSupplier FOREVER_WAIT_TIME_SUPPLIER = () -> Constants.WAIT_FOREVER;

    private static WaitTimeSupplier INSTANCE = new SimpleWaitTimeSupplier();

    private SimpleWaitTimeSupplier() {
        calledOnce(this.getClass());
    }

    static WaitTimeSupplier simpleWaitTimeSupplier() {
        return create();
    }

    static WaitTimeSupplier create() {
        return INSTANCE;
    }

    @Override
    public LongSupplier infinite() {
        return FOREVER_WAIT_TIME_SUPPLIER;
    }

    @Override
    public LongSupplier timed(long time) {
        return createWaitTimeSupplier(time);
    }

    private LongSupplier createWaitTimeSupplier(long requestedWaitTime) {
        final long startTime = System.currentTimeMillis();
        return () -> getElapsedTime(startTime, requestedWaitTime);
    }

    private long getElapsedTime(long startTime, long requestedWaitTime) {
        final long timeElapsed = (System.currentTimeMillis() - startTime);
        final long needToWait = requestedWaitTime - timeElapsed;
        // returns value in [0, pollInterval]
        // zero means run out of time.
        return max(0, needToWait);
    }
}
