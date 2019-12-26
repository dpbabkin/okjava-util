package okjava.util.blockandwait.supplier;

import okjava.util.blockandwait.Constants;
import okjava.util.check.MathCheck;

import java.util.function.LongSupplier;

import static java.lang.Math.min;

class PollerWaitTimeSupplier implements WaitTimeSupplier {

    private final LongSupplier getPollIntervalWaitTime;

    private PollerWaitTimeSupplier(long pollInterval) {
        MathCheck.positive(pollInterval);
        this.getPollIntervalWaitTime = () -> pollInterval;
    }

    public static WaitTimeSupplier create(long pollInterval) {
        assert pollInterval != Constants.WAIT_FOREVER : pollInterval;
        return new PollerWaitTimeSupplier(pollInterval);
    }

    @Override
    public LongSupplier timed(long time) {
        if (time == Constants.WAIT_FOREVER) {
            return getPollIntervalWaitTime;
        }
        final LongSupplier longSupplier = SimpleWaitTimeSupplier.create().timed(time);
        return () -> min(longSupplier.getAsLong(), getPollIntervalWaitTime.getAsLong());
    }
}
