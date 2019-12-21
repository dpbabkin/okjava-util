package okjava.util.blockandwait.legacy;

import okjava.util.blockandwait.BlockAndWaitUpdatable;
import okjava.util.blockandwait.WaitTimeSupplierFactory;
import okjava.util.blockandwait.general.BlockAndWaitGeneralImpl;
import okjava.util.blockandwait.general.BlockAndWaitGeneralUpdatable;

import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

import static okjava.util.NotNull.notNull;

final class MainBlockAndWait implements BlockAndWaitUpdatable {
    private final BlockAndWaitGeneralUpdatable blockAndWaitUpdatable = BlockAndWaitGeneralImpl.create();
    private final WaitTimeSupplierFactory waitTimeSupplierFactory;

    private MainBlockAndWait(WaitTimeSupplierFactory waitTimeSupplierFactory) {
        this.waitTimeSupplierFactory = notNull(waitTimeSupplierFactory);
    }

    static BlockAndWaitUpdatable create(WaitTimeSupplierFactory waitTimeSupplierFactory) {
        return new MainBlockAndWait(waitTimeSupplierFactory);
    }

    public void onUpdate() {
        blockAndWaitUpdatable.onUpdate();
    }

    @Override
    public <V> V await(Supplier<V> isEventHappened) throws InterruptedException {
        return doAwait(isEventHappened, waitTimeSupplierFactory.infinite());
    }

    @Override
    public <V> V await(Supplier<V> isEventHappened, long time, TimeUnit timeUnit) throws InterruptedException {
        return doAwait(isEventHappened, waitTimeSupplierFactory.timed(time, timeUnit));
    }

    private <V> V doAwait(Supplier<V> isEventHappened, LongSupplier needToWaitProvider) throws InterruptedException {
        return blockAndWaitUpdatable.await(isEventHappened, needToWaitProvider);
    }
}
