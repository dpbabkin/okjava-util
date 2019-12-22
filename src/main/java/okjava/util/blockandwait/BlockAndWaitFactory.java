package okjava.util.blockandwait;

import okjava.util.annotation.Utility;
import okjava.util.blockandwait.supplier.WaitTimeSupplierFactory;
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
        return BlockAndWaitImpl.create(WaitTimeSupplierFactory.create());
    }

    public static BlockAndWaitUpdatable createWithDefaultPoll(long pollInterval) {
        return BlockAndWaitImpl.create(WaitTimeSupplierFactory.createWithPoll(pollInterval));
    }

    public static BlockAndWaitUpdatable createWithDefaultPoll() {
        return BlockAndWaitImpl.create(WaitTimeSupplierFactory.createWithDefaultPoll());
    }
}
