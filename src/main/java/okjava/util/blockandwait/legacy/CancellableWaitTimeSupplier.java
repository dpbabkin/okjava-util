package okjava.util.blockandwait.legacy;

import okjava.util.blockandwait.Constants;
import okjava.util.blockandwait.supplier.WaitTimeSupplier;

import java.util.function.LongSupplier;
import java.util.function.Supplier;

import static okjava.util.NotNull.notNull;

class CancellableWaitTimeSupplier extends DelegateWaitTimeSupplier implements WaitTimeSupplier {

    final private Supplier<Boolean> isCancelled;

    private CancellableWaitTimeSupplier(WaitTimeSupplier delegate, Supplier<Boolean> isCancelled) {
        super(delegate);
        this.isCancelled = notNull(isCancelled);
    }

    static WaitTimeSupplier create(WaitTimeSupplier delegate, Supplier<Boolean> cancelProvider) {
        return new CancellableWaitTimeSupplier(delegate, cancelProvider);
    }

    @Override
    public LongSupplier timed(long time) {
        return () -> {
            if (isCancelled.get()) {
                return Constants.NO_NEED_TO_WAIT;
            }
            return CancellableWaitTimeSupplier.super.timed(time).getAsLong();
        };
    }
}
