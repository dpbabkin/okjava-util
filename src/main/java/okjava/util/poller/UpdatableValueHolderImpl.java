package okjava.util.poller;

import okjava.util.poller.listener.SupplierListener;
import okjava.util.poller.listener.SupplierListenerCollection;
import okjava.util.poller.listener.SupplierListenerCollectionImpl;
import okjava.util.poller.poller.Poller;
import okjava.util.poller.poller.PollerWithSupplier;
import okjava.util.poller.poller.PollerWithSupplierImpl;
import okjava.util.thread.ExecutorFactory;

import java.util.concurrent.Executor;
import java.util.function.Supplier;

import static okjava.util.NotNull.notNull;

class UpdatableValueHolderImpl<V> implements UpdatableValueHolder<V> {

    private final PollerWithSupplier<V> poller;
    private final SupplierListenerCollection<V> supplierListenerCollection;

    private final Runnable supplierListenerCollectionUpdater;
    private final Supplier<V> valueSupplier;
    private final Runnable updateAll = new AllUpdater();

    private static final Executor EXECUTOR = ExecutorFactory.getInstance().getExecutor();

    static <V> UpdatableValueHolder<V> create(Supplier<V> valueSupplier) {
        return new UpdatableValueHolderImpl<>(valueSupplier);
    }

    private UpdatableValueHolderImpl(Supplier<V> valueSupplier) {
        this.valueSupplier = notNull(valueSupplier);
        this.poller = PollerWithSupplierImpl.create(valueSupplier);
        SupplierListenerCollectionImpl<V> supplierListenerCollection = SupplierListenerCollectionImpl.create();
        this.supplierListenerCollection = supplierListenerCollection;
        this.supplierListenerCollectionUpdater = new SupplierListenerCollectionUpdater<>(supplierListenerCollection, valueSupplier);
    }

    private void onUpdateNative() {
        EXECUTOR.execute(poller);
        EXECUTOR.execute(supplierListenerCollectionUpdater);
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


    private final static class SupplierListenerCollectionUpdater<V> implements Runnable {
        private final SupplierListener<V> supplierListener;
        private final Supplier<V> valueSupplier;

        private SupplierListenerCollectionUpdater(SupplierListener<V> supplierListener, Supplier<V> valueSupplier) {
            this.supplierListener = supplierListener;
            this.valueSupplier = valueSupplier;
        }

        @Override
        public void run() {
            this.supplierListener.accept(this.valueSupplier);
        }
    }

    private final class AllUpdater implements Runnable {

        @Override
        public void run() {
            UpdatableValueHolderImpl.this.onUpdateNative();
        }
    }
}
