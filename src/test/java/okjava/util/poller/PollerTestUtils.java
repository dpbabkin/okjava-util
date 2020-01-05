package okjava.util.poller;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;
import okjava.util.poller.poller.Poller;
import okjava.util.string.ToStringBuffer;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static okjava.util.WaitTestUtils.DEFAULT_WAIT_TIME;
import static okjava.util.WaitTestUtils.catchInterrupted;
import static okjava.util.check.Never.neverNeverCalled;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

@Utility
public class PollerTestUtils {
    ;

    PollerTestUtils(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static <V> void waitAssertValue(Poller<V> poller, final V expectedValue) {
        waitAssertValue(poller, expectedValue, DEFAULT_WAIT_TIME);
    }

    public static <V> void waitAssertValue(Poller<V> poller, final V expectedValue, long time, TimeUnit timeUnit)  {
        waitAssertValue(poller, expectedValue, timeUnit.toMillis(time));
    }

    public static <V> void waitAssertValue(Poller<V> poller, final V expectedValue, long time)  {
        Optional<V> value = null;
        try {
            value = poller.poll(v -> v.equals(expectedValue), time);
        } catch (InterruptedException e) {
            catchInterrupted(e);
        }
        if (value.isEmpty()) {
            assertThat(poller.get(), is(expectedValue));
        }
    }

    public static <V> void waitAssertValue(ValueHolder<V> valueHolder, final V expectedValue) {
        waitAssertValue(valueHolder, expectedValue, DEFAULT_WAIT_TIME);
    }

    public static <V> void waitAssertValue(ValueHolder<V> valueHolder, final V expectedValue, long time, TimeUnit timeUnit) {
        waitAssertValue(valueHolder, expectedValue, timeUnit.toMillis(time));
    }

    public static <V> void waitAssertValue(ValueHolder<V> valueHolder, final V expectedValue, long time)  {
        waitAssertValue(valueHolder.getPoller(), expectedValue, time);
    }
}
