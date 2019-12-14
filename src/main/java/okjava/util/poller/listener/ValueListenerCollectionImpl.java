package okjava.util.poller.listener;

import okjava.util.thread.ExecutorFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/24/2019
 * 21:14.
 */
public class ValueListenerCollectionImpl<V> implements ListenerCollection<V> , Listener<V>{

    private final static Executor EXECUTOR = ExecutorFactory.getInstance().getTaskQueueConfinedExecutor();

    private final List<Listener<V>> listeners = new CopyOnWriteArrayList<>();

    public static <V> ValueListenerCollectionImpl<V> create() {
        return new ValueListenerCollectionImpl<>();
    }

    private ValueListenerCollectionImpl() {
    }

    @Override
    public void registerListener(Listener<V> listener) {
        listeners.add(listener);
    }

    private void onUpdateInParallel(V value) {
        listeners.forEach(l -> EXECUTOR.execute(() -> l.accept(value)));
    }

    @Override
    public void accept(V value) {
        EXECUTOR.execute(() -> onUpdateInParallel(value));
    }
}
