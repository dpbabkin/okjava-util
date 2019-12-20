package okjava.util.poller.listener;

import com.google.common.collect.Maps;
import okjava.util.id.TimeSequenceIdGeneratorFactory;
import okjava.util.thread.ExecutorFactory;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/24/2019
 * 21:14.
 */
public class SupplierListenerCollectionImpl<V> implements SupplierListenerCollection<V>, SupplierListener<V> {

    private final static Executor EXECUTOR = ExecutorFactory.getInstance().getTaskQueueConfinedExecutor();

    private final Map<Long, SupplierListener<V>> listeners = Maps.newConcurrentMap();

    public static <V> SupplierListenerCollectionImpl<V> create() {
        return new SupplierListenerCollectionImpl<>();
    }

    private SupplierListenerCollectionImpl() {
    }

    @Override
    public Runnable registerListener(SupplierListener<V> listener) {
        final Long id = TimeSequenceIdGeneratorFactory.timeSequenceIdGenerator().generate();
        listeners.put(id, listener);
        return () -> listeners.remove(id);

    }

    private void onUpdateInParallel(Supplier<V> supplier) {
        listeners.values().forEach(l -> EXECUTOR.execute(() -> l.accept(supplier)));
    }

    @Override
    public void accept(Supplier<V> supplier) {
        EXECUTOR.execute(() -> onUpdateInParallel(supplier));
    }
}
