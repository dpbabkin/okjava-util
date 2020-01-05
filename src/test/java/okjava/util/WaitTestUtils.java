package okjava.util;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;
import okjava.util.string.ToStringBuffer;

import java.util.concurrent.TimeUnit;

import static okjava.util.check.Never.neverNeverCalled;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/17/2016
 * 16:31.
 */
@Utility
public enum WaitTestUtils {
    ;

    WaitTestUtils(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static final long DEFAULT_WAIT_TIME = 1_000L;
    public static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.MILLISECONDS;

    public static void interruptAndWait(Thread thread) {
        interruptAndWait(thread, DEFAULT_WAIT_TIME);
    }

    public static void interruptAndWait(Thread thread, long time) {
        thread.interrupt();
        try {
            thread.join(time);
        } catch (InterruptedException e) {
            catchInterrupted(e);
        }
        assertThat(thread.getState(), is(Thread.State.TERMINATED));
    }
    public static void catchInterrupted(InterruptedException e) {
        Thread.currentThread().interrupt();
        fail(ToStringBuffer.string("thread interrupted").addThread().toString());
    }
}
