package okjava.util.blockandwait.legacy;

import okjava.util.blockandwait.BlockAndWaitUpdatable;
import okjava.util.blockandwait.Constants;
import okjava.util.blockandwait.supplier.WaitTimeSupplier;
import okjava.util.blockandwait.supplier.WaitTimeSupplierFactory;
import okjava.util.check.MathCheck;

import java.util.function.Supplier;

import static okjava.util.NotNull.notNull;

public class BlockAndWaitBuilder {
    //static final long DEFAULT_POLL_INTERVAL = 10;
    private long pollInterval = Constants.WAIT_FOREVER; //polling is disabled by default.
    private Supplier<Boolean> cancelProvider;

    private BlockAndWaitBuilder() {
    }

    public static BlockAndWaitBuilder builder() {
        return new BlockAndWaitBuilder();
    }

    public BlockAndWaitBuilder withPollInterval(long pollInterval) {
        this.pollInterval = MathCheck.positive(pollInterval);
        return this;
    }

    public BlockAndWaitBuilder withDefaultPollInterval() {
        return withPollInterval(WaitTimeSupplierFactory.DEFAULT_POLL_INTERVAL);
    }

    public BlockAndWaitBuilder withCancelProvider(Supplier<Boolean> cancelProvider) {
        this.cancelProvider = notNull(cancelProvider);
        return this;
    }

    public BlockAndWaitUpdatable build() {
        WaitTimeSupplier waitTimeSupplier =
                WaitTimeSupplierFactory.createWithPoll(this.pollInterval);

        if (cancelProvider != null) {
            waitTimeSupplier = CancellableWaitTimeSupplier
                    .create(waitTimeSupplier, cancelProvider);
        }
        throw new UnsupportedOperationException("not implemented");
        //return MainBlockAndWait.create(waitTimeSupplier);
    }
}
