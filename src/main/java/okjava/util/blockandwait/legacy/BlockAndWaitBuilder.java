package okjava.util.blockandwait.legacy;

import okjava.util.blockandwait.BlockAndWaitUpdatable;
import okjava.util.blockandwait.PollerWaitTimeSupplierFactory;
import okjava.util.blockandwait.WaitTimeSupplierFactory;
import okjava.util.blockandwait.general.BlockAndWaitGeneralImpl;
import okjava.util.check.MathCheck;

import java.util.function.Supplier;

import static okjava.util.NotNull.notNull;

public class BlockAndWaitBuilder {
    //static final long DEFAULT_POLL_INTERVAL = 10;
    private long pollInterval = BlockAndWaitGeneralImpl.WAIT_FOREVER; //polling is disabled by default.
    private Supplier<Boolean> cancelProvider;

    private BlockAndWaitBuilder() {
    }

    public static BlockAndWaitBuilder create() {
        return new BlockAndWaitBuilder();
    }

    public BlockAndWaitBuilder withPollInterval(long pollInterval) {
        this.pollInterval = MathCheck.positive(pollInterval);
        return this;
    }

    public BlockAndWaitBuilder withDefaultPollInterval() {
        return withPollInterval(PollerWaitTimeSupplierFactory.DEFAULT_POLL_INTERVAL);
    }

    public BlockAndWaitBuilder withCancelProvider(Supplier<Boolean> cancelProvider) {
        this.cancelProvider = notNull(cancelProvider);
        return this;
    }

    public BlockAndWaitUpdatable build() {
        WaitTimeSupplierFactory waitTimeSupplierFactory =
                PollerWaitTimeSupplierFactory.create(this.pollInterval);

        if (cancelProvider != null) {
            waitTimeSupplierFactory = CancellableWaitTimeSupplierFactory
                    .create(waitTimeSupplierFactory, cancelProvider);
        }
        return MainBlockAndWait.create(waitTimeSupplierFactory);
    }
}
