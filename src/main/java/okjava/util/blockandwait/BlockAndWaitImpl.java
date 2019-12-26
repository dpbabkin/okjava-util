package okjava.util.blockandwait;

import okjava.util.blockandwait.core.BlockAndWaitGeneralImpl;
import okjava.util.blockandwait.core.BlockAndWaitGeneralUpdatable;
import okjava.util.blockandwait.supplier.WaitTimeSupplier;

import java.util.function.LongSupplier;
import java.util.function.Supplier;

import static okjava.util.NotNull.notNull;

final class BlockAndWaitImpl implements BlockAndWaitUpdatable {
    private final BlockAndWaitGeneralUpdatable blockAndWaitUpdatable = BlockAndWaitGeneralImpl.create();
    private final WaitTimeSupplier waitTimeSupplier;

    private BlockAndWaitImpl(WaitTimeSupplier waitTimeSupplier) {
        this.waitTimeSupplier = notNull(waitTimeSupplier);
    }

    static BlockAndWaitUpdatable create(WaitTimeSupplier waitTimeSupplier) {
        return new BlockAndWaitImpl(waitTimeSupplier);
    }

    public void onUpdate() {
        blockAndWaitUpdatable.onUpdate();
    }

    @Override
    public <V> V await(Supplier<V> isEventHappened) throws InterruptedException {
        return doAwait(isEventHappened, waitTimeSupplier.infinite());
    }

    @Override
    public <V> V await(Supplier<V> isEventHappened, long time) throws InterruptedException {
        if (time == Constants.WAIT_FOREVER) {
            return doAwait(isEventHappened, waitTimeSupplier.infinite());
        }
        return doAwait(isEventHappened, waitTimeSupplier.timed(time));
    }

    private <V> V doAwait(Supplier<V> isEventHappened, LongSupplier needToWaitProvider) throws InterruptedException {
        return blockAndWaitUpdatable.await(isEventHappened, needToWaitProvider);
    }
}
