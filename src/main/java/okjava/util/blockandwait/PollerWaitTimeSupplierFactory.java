package okjava.util.blockandwait;

import okjava.util.blockandwait.general.BlockAndWaitGeneralImpl;
import okjava.util.check.MathCheck;

import java.util.function.LongSupplier;

import static java.lang.Math.min;

public class PollerWaitTimeSupplierFactory implements WaitTimeSupplierFactory {
    public static final long DEFAULT_POLL_INTERVAL = 10;
    private final LongSupplier getPollIntervalWaitTime;

    private PollerWaitTimeSupplierFactory(long pollInterval) {
        MathCheck.positive(pollInterval);
        this.getPollIntervalWaitTime = () -> pollInterval;
    }

    public static WaitTimeSupplierFactory createDefault() {
        return create(DEFAULT_POLL_INTERVAL);
    }

    public static WaitTimeSupplierFactory create(long pollInterval) {
        if (pollInterval == BlockAndWaitGeneralImpl.WAIT_FOREVER) {
            return SimpleWaitTimeSupplierFactory.create();
        }
        return new PollerWaitTimeSupplierFactory(pollInterval);
    }

    @Override
    public LongSupplier infinite() {
        return getPollIntervalWaitTime;
    }

    @Override
    public LongSupplier timed(long time) {
        final LongSupplier longSupplier = SimpleWaitTimeSupplierFactory.create().timed(time);
        return () -> min(longSupplier.getAsLong(), getPollIntervalWaitTime.getAsLong());
    }
}
