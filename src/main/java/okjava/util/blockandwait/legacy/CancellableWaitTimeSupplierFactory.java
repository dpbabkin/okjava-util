package okjava.util.blockandwait.legacy;

import okjava.util.blockandwait.WaitTimeSupplierFactory;
import okjava.util.blockandwait.general.BlockAndWaitGeneralImpl;

import java.util.function.LongSupplier;
import java.util.function.Supplier;

import static okjava.util.NotNull.notNull;

class CancellableWaitTimeSupplierFactory extends DelegateWaitTimeSupplierFactory implements WaitTimeSupplierFactory {

    final private Supplier<Boolean> isCancelled;

    private CancellableWaitTimeSupplierFactory(WaitTimeSupplierFactory delegate, Supplier<Boolean> isCancelled) {
        super(delegate);
        this.isCancelled = notNull(isCancelled);
    }

    static WaitTimeSupplierFactory create(WaitTimeSupplierFactory delegate, Supplier<Boolean> cancelProvider) {
        return new CancellableWaitTimeSupplierFactory(delegate, cancelProvider);
    }

    @Override
    public LongSupplier infinite() {
        return () -> {
            if (isCancelled.get()) {
                return BlockAndWaitGeneralImpl.NO_NEED_TO_WAIT;
            }
            return CancellableWaitTimeSupplierFactory.super.infinite().getAsLong();
        };
    }

    @Override
    public LongSupplier timed(long time) {
        return () -> {
            if (isCancelled.get()) {
                return BlockAndWaitGeneralImpl.NO_NEED_TO_WAIT;
            }
            return CancellableWaitTimeSupplierFactory.super.timed(time).getAsLong();
        };
    }

//    private LongSupplier wrapAbort(LongSupplier delegate) {
//        return () -> {
//            if (isCancelled.get() == true) {
//                return BlockAndWaitGeneralImpl.NO_NEED_TO_WAIT;
//            }
//            return delegate.getAsLong();
//        };
//    }
}
