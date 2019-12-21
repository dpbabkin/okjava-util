package okjava.util.blockandwait.legacy;

import okjava.util.check.MathCheck;

import java.util.function.LongSupplier;

import static java.lang.Math.min;

final class PollerBlockAndWait extends BaseBlockAndWait {
    private final LongSupplier getPollIntervalWaitTime;

    private PollerBlockAndWait(long pollInterval) {
        MathCheck.positive(pollInterval);
        this.getPollIntervalWaitTime = () -> pollInterval;
    }

    static BaseBlockAndWait create(long pollInterval) {
        return new PollerBlockAndWait(pollInterval);
    }

    @Override
    protected LongSupplier getInfiniteWaitSupplier() {
        return getPollIntervalWaitTime;
    }

    @Override
    protected long getElapsedTime(long startTime, long requestedWaitTime) {
        final long s = super.getElapsedTime(startTime, requestedWaitTime);
        return min(s, getPollIntervalWaitTime.getAsLong());
    }
}
