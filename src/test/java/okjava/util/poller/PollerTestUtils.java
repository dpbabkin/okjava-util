package okjava.util.poller;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;
import okjava.util.condition.BlockingWaitForEvent;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static okjava.util.check.Never.neverNeverCalled;

@Utility
public class PollerTestUtils {
    ;

    private static final long DEFAULT_WAIT_TIME = 3L;
    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;

    PollerTestUtils(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static <V> void assertValue(Supplier<V> supplier, final V expectedValue) throws InterruptedException {
        assertValue(supplier, expectedValue, DEFAULT_WAIT_TIME, DEFAULT_TIME_UNIT);
    }

    public static <V> void assertValue(Supplier<V> supplier, final V expectedValue, long time, TimeUnit timeUnit) throws InterruptedException {
        BlockingWaitForEvent waiter = BlockingWaitForEvent.createWithPoll();
        waiter.waiter(() -> supplier.get().equals(expectedValue)).await(time, timeUnit).assertTrue();
    }

    public static <V> void assertValue(ValueHolder<V> valueHolder, final V expectedValue) throws InterruptedException {
        assertValue(valueHolder, expectedValue, DEFAULT_WAIT_TIME, DEFAULT_TIME_UNIT);
    }

    public static <V> void assertValue(ValueHolder<V> valueHolder, final V expectedValue, long time, TimeUnit timeUnit) throws InterruptedException {
        BlockingWaitForEvent waiter = BlockingWaitForEvent.create();
        Runnable removeListener = valueHolder.getSupplierListenerCollection().registerListener(vSupplier -> waiter.onUpdate());
        try {
            waiter.waiter(() -> valueHolder.get().equals(expectedValue)).await(time, timeUnit).assertTrue();
        } finally {
            removeListener.run();
        }
    }
}
