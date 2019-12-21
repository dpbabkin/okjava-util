package okjava.util.blockandwait.legacy;

import okjava.util.blockandwait.WaitTimeSupplierFactory;

import java.util.function.LongSupplier;

import static okjava.util.NotNull.notNull;

abstract class DelegateWaitTimeSupplierFactory implements WaitTimeSupplierFactory {

    private final WaitTimeSupplierFactory delegate;

    DelegateWaitTimeSupplierFactory(WaitTimeSupplierFactory delegate) {
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
