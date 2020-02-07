package okjava.util.blockandwait.supplier;

import okjava.util.annotation.Utility;
import okjava.util.blockandwait.Constants;
import okjava.util.check.Never;

import static okjava.util.check.Never.neverNeverCalled;

@Utility
public enum WaitTimeSupplierFactory {
    ;

    public static final long DEFAULT_POLL_INTERVAL = 3;

    WaitTimeSupplierFactory(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static WaitTimeSupplier create() {
        return SimpleWaitTimeSupplier.create();
    }

    public static WaitTimeSupplier createWithPoll(long pollInterval) {
        if (pollInterval == Constants.WAIT_FOREVER) {
            return create();
        }
        return PollerWaitTimeSupplier.create(pollInterval);

    }

    public static WaitTimeSupplier createWithDefaultPoll() {
        return createWithPoll(DEFAULT_POLL_INTERVAL);
    }
}
