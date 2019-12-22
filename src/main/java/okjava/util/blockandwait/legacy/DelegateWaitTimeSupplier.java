package okjava.util.blockandwait.legacy;

import okjava.util.blockandwait.supplier.WaitTimeSupplier;

import java.util.function.LongSupplier;

import static okjava.util.NotNull.notNull;

abstract class DelegateWaitTimeSupplier implements WaitTimeSupplier {

    private final WaitTimeSupplier delegate;

    DelegateWaitTimeSupplier(WaitTimeSupplier delegate) {
        this.delegate = notNull(delegate);
    }

    @Override
    public LongSupplier infinite() {
        return delegate.infinite();
    }

    @Override
    public LongSupplier timed(long time) {
        return delegate.timed(time);
    }
}
