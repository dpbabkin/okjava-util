package okjava.util.blockandwait.legacy;

import okjava.util.blockandwait.Constants;

import java.util.function.LongSupplier;

final class SimpleBlockAndWait extends AbstractBlockAndWait {
    static private final LongSupplier FOREVER_WAIT_TIME_SUPPLIER = () -> Constants.WAIT_FOREVER;

    private SimpleBlockAndWait() {
    }

    static AbstractBlockAndWait create() {
        return new SimpleBlockAndWait();
    }

    @Override
    protected LongSupplier getInfiniteWaitSupplier() {
        return FOREVER_WAIT_TIME_SUPPLIER;
    }
}
