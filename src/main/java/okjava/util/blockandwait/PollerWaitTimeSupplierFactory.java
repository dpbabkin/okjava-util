package okjava.util.blockandwait;

import okjava.util.check.MathCheck;

import java.util.function.LongSupplier;

import static java.lang.Math.min;

public class PollerWaitTimeSupplierFactory implements WaitTimeSupplierFactory {

    private final LongSupplier getPollIntervalWaitTime;

    private PollerWaitTimeSupplierFactory(long pollInterval) {
        MathCheck.positive(pollInterval);
        this.getPollIntervalWaitTime = () -> pollInterval;
    }

    public static WaitTimeSupplierFactory create(long pollInterval) {
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
