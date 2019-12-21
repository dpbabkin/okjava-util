package okjava.util.blockandwait.legacy;

import okjava.util.annotation.Utility;
import okjava.util.blockandwait.BlockAndWaitUpdatable;
import okjava.util.blockandwait.PollerWaitTimeSupplierFactory;
import okjava.util.blockandwait.SimpleWaitTimeSupplierFactory;
import okjava.util.blockandwait.general.BlockAndWaitGeneralImpl;
import okjava.util.check.Never;

import static okjava.util.blockandwait.legacy.BlockAndWaitBuilder.DEFAULT_POLL_INTERVAL;
import static okjava.util.check.Never.neverNeverCalled;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/5/2019
 * 09:38.
 */
@Utility
@Deprecated //use builder instead.
public enum BlockAndWaitFactory {
    ;

    BlockAndWaitFactory(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static BlockAndWaitUpdatable create() {
        return MainBlockAndWait.create(SimpleWaitTimeSupplierFactory.create());
    }

    public static BlockAndWaitUpdatable createWithPoll(long pollInterval) {
        if (pollInterval == BlockAndWaitGeneralImpl.WAIT_FOREVER) {
            return create();
        }
        return MainBlockAndWait.create(PollerWaitTimeSupplierFactory.create(pollInterval));
    }

    public static BlockAndWaitUpdatable createWithPoll() {
        return createWithPoll(DEFAULT_POLL_INTERVAL);
    }
}
