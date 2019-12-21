package okjava.util.poller;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;
import okjava.util.poller.poller.Poller;

import java.util.concurrent.TimeUnit;

import static okjava.util.check.Never.neverNeverCalled;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Utility
public class PollerTestUtils {
    ;

    private static final long DEFAULT_WAIT_TIME = 3L;
    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;

    PollerTestUtils(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static <V> void assertValue(Poller<V> poller, final V expectedValue) throws InterruptedException {
        assertValue(poller, expectedValue, DEFAULT_WAIT_TIME, DEFAULT_TIME_UNIT);
    }

    public static <V> void assertValue(Poller<V> poller, final V expectedValue, long time, TimeUnit timeUnit) throws InterruptedException {
        assertThat(poller.poll(v -> v.equals(expectedValue), time, timeUnit).isPresent(), is(true));
    }

    public static <V> void assertValue(ValueHolder<V> valueHolder, final V expectedValue) throws InterruptedException {
        assertValue(valueHolder, expectedValue, DEFAULT_WAIT_TIME, DEFAULT_TIME_UNIT);
    }

    public static <V> void assertValue(ValueHolder<V> valueHolder, final V expectedValue, long time, TimeUnit timeUnit) throws InterruptedException {
        assertValue(valueHolder.getPoller(), expectedValue, time, timeUnit);
    }
}
