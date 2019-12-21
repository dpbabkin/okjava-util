package okjava.util.blockandwait.legacy;

import okjava.util.annotation.Utility;
import okjava.util.blockandwait.BlockAndWaitUpdatable;
import okjava.util.blockandwait.PollerWaitTimeSupplierFactory;
import okjava.util.blockandwait.SimpleWaitTimeSupplierFactory;
import okjava.util.check.Never;

import static okjava.util.check.Never.neverNeverCalled;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/5/2019
 * 09:38.
 */
@Utility
public enum BlockAndWaitFactory {
    ;

    BlockAndWaitFactory(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static BlockAndWaitUpdatable create() {
        return MainBlockAndWait.create(SimpleWaitTimeSupplierFactory.create());
    }

    public static BlockAndWaitUpdatable createWithPoll(long pollInterval) {
        return MainBlockAndWait.create(PollerWaitTimeSupplierFactory.create(pollInterval));
    }

    public static BlockAndWaitUpdatable createWithPoll() {
        return MainBlockAndWait.create(PollerWaitTimeSupplierFactory.createDefault());
    }
}
