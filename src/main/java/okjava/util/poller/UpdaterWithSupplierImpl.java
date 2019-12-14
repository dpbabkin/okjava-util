package okjava.util.poller;

import java.util.function.Supplier;

public class UpdaterWithSupplierImpl<V> extends AbstractBaseUpdater<V, PollerWithSupplier<V>> implements UpdaterWithSupplier<V> {

    private final Runnable updateGenericPoller;
    public static <V> UpdaterWithSupplier<V> create(Supplier<V> supplier) {
        return new UpdaterWithSupplierImpl<>(supplier);
    }

    private UpdaterWithSupplierImpl(Supplier<V> supplier) {
        super(PollerWithSupplierImpl.create(supplier));
        this.updateGenericPoller = ()->getGenericPoller().onUpdate();
    }

    @Override
    public V get() {
        return getGenericPoller().get();
    }

    @Override
    public void onUpdate() {
        super.onUpdateAsync();
    }

    protected void onUpdateNative() {
        EXECUTOR.execute(updateGenericPoller);
        super.onUpdateNative();
    }
}
