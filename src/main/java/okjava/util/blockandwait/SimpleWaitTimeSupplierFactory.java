package okjava.util.blockandwait;

import okjava.util.annotation.Singleton;
import okjava.util.blockandwait.general.BlockAndWaitGeneralImpl;

import java.util.function.LongSupplier;

import static java.lang.Math.max;
import static okjava.util.check.Once.calledOnce;

@Singleton
public class SimpleWaitTimeSupplierFactory implements WaitTimeSupplierFactory {

    static private final LongSupplier FOREVER_WAIT_TIME_SUPPLIER = () -> BlockAndWaitGeneralImpl.WAIT_FOREVER;

    private static WaitTimeSupplierFactory INSTANCE = new SimpleWaitTimeSupplierFactory();

    private SimpleWaitTimeSupplierFactory() {
        calledOnce(this.getClass());
    }

    public static WaitTimeSupplierFactory waitTimeSupplierFactory() {
        return create();
    }

    public static WaitTimeSupplierFactory create() {
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
