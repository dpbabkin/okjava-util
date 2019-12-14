package okjava.util.poller.listener;

import okjava.util.thread.ExecutorFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/24/2019
 * 21:14.
 */
public class SupplierListenerCollectionImpl<V> implements SupplierListenerCollection<V>, SupplierListener<V> {

    private final static Executor EXECUTOR = ExecutorFactory.getInstance().getTaskQueueConfinedExecutor();

    private final List<SupplierListener<V>> listeners = new CopyOnWriteArrayList<>();

    public static <V> SupplierListenerCollectionImpl<V> create() {
        return new SupplierListenerCollectionImpl<>();
    }

    private SupplierListenerCollectionImpl() {
    }

    @Override
    public void registerListener(SupplierListener<V> listener) {
        listeners.add(listener);
    }

    private void onUpdateInParallel(Supplier<V> supplier) {
        listeners.forEach(l -> EXECUTOR.execute(() -> l.accept(supplier)));
    }

    @Override
    public void accept(Supplier<V> supplier) {
        EXECUTOR.execute(() -> onUpdateInParallel(supplier));
    }
}
