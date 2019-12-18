package okjava.util.poller;

import okjava.util.poller.listener.SupplierListenerCollection;
import okjava.util.poller.listener.SupplierListenerCollectionImpl;
import okjava.util.poller.poller.Poller;
import okjava.util.poller.poller.PollerWithSupplier;
import okjava.util.poller.poller.PollerWithSupplierImpl;
import okjava.util.thread.ExecutorFactory;

import java.util.concurrent.Executor;
import java.util.function.Supplier;

import static okjava.util.NotNull.notNull;

public class ValueHolderImpl<V> implements ValueHolder<V>, Updatable {

    private final PollerWithSupplier<V> poller;
    private final SupplierListenerCollection<V> supplierListenerCollection;
    //private final SupplierListener<V> supplierListener;
    private final Runnable updatesupplierListenerCollection;

    private final Supplier<V> valueSupplier;
    private final Runnable updateAll = this::onUpdateNative;

    private static final Executor EXECUTOR = ExecutorFactory.getInstance().getExecutor();

    public static <V> ValueHolderImpl<V> create(Supplier<V> valueSupplier) {
        return new ValueHolderImpl<>(valueSupplier);
    }

    private ValueHolderImpl(Supplier<V> valueSupplier) {
        this.valueSupplier = notNull(valueSupplier);
        this.poller = PollerWithSupplierImpl.create(valueSupplier);
        SupplierListenerCollectionImpl<V> supplierListenerCollection = SupplierListenerCollectionImpl.create();
        this.supplierListenerCollection = supplierListenerCollection;
        this.updatesupplierListenerCollection = () -> supplierListenerCollection.accept(valueSupplier);
    }

    private void onUpdateNative() {
        EXECUTOR.execute(poller);
        EXECUTOR.execute(updatesupplierListenerCollection);
    }

    @Override
    public Poller<V> getPoller() {
        return poller;
    }

    @Override
    public SupplierListenerCollection<V> getSupplierListenerCollection() {
        return supplierListenerCollection;
    }

    @Override
    public void onUpdate() {
        EXECUTOR.execute(updateAll);
    }

    @Override
    public V get() {
        return valueSupplier.get();
    }
}
