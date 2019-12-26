package okjava.util.poller;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;
import okjava.util.poller.poller.Poller;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static okjava.util.WaitTestUtils.DEFAULT_TIME_UNIT;
import static okjava.util.WaitTestUtils.DEFAULT_WAIT_TIME;
import static okjava.util.check.Never.neverNeverCalled;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Utility
public class PollerTestUtils {
    ;

    PollerTestUtils(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static <V> void waitAssertValue(Poller<V> poller, final V expectedValue) throws InterruptedException {
        waitAssertValue(poller, expectedValue, DEFAULT_WAIT_TIME, DEFAULT_TIME_UNIT);
    }

    public static <V> void waitAssertValue(Poller<V> poller, final V expectedValue, long time, TimeUnit timeUnit) throws InterruptedException {
        waitAssertValue(poller, expectedValue, timeUnit.toMillis(time));
    }

    public static <V> void waitAssertValue(Poller<V> poller, final V expectedValue, long time) throws InterruptedException {
        Optional<V> value = poller.poll(v -> v.equals(expectedValue), time);
        assertThat(value.isPresent(), is(true));
    }

    public static <V> void waitAssertValue(ValueHolder<V> valueHolder, final V expectedValue) throws InterruptedException {
        waitAssertValue(valueHolder, expectedValue, DEFAULT_WAIT_TIME, DEFAULT_TIME_UNIT);
    }

    public static <V> void waitAssertValue(ValueHolder<V> valueHolder, final V expectedValue, long time, TimeUnit timeUnit) throws InterruptedException {
        waitAssertValue(valueHolder, expectedValue, timeUnit.toMillis(time));
    }

    public static <V> void waitAssertValue(ValueHolder<V> valueHolder, final V expectedValue, long time) throws InterruptedException {
        waitAssertValue(valueHolder.getPoller(), expectedValue, time);
    }
}
