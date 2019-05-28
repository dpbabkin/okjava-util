package okjava.util.testutils;

import static okjava.util.check.Never.neverNeverCalled;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/28/2019
 * 10:57.
 */
@Utility
public enum PollWaitForCondition {
    ;

    PollWaitForCondition(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    private final static long POLL_INTERVAL = 100L;

    public static boolean pollWait(Supplier<Boolean> test, long time, TimeUnit timeUnit) throws InterruptedException {

        final long beginTime = System.currentTimeMillis();
        final long requestedWaitTime = timeUnit.toMillis(time);
        for (; ; ) {

            if (test.get() == true) {
                return true;
            }

            long timeElapsed = (System.currentTimeMillis() - beginTime);
            long needToWaitSoFar = requestedWaitTime - timeElapsed;
            if (needToWaitSoFar <= 0) {
                return false;
            }
            Thread.sleep(Math.min(POLL_INTERVAL, needToWaitSoFar));
        }
    }

    public static boolean pollWait(Supplier<Boolean> test) throws InterruptedException {
        return pollWait(test, 1, TimeUnit.MINUTES);
    }
}
