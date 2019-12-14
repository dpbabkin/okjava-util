package okjava.util.poller;

import okjava.util.thread.ExecutorFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/24/2019
 * 21:14.
 */
class ListenerCollection<V> implements ListenerRegister<V> {

    private final static Executor EXECUTOR = ExecutorFactory.getInstance().getExecutor();

    private final List<Listener<V>> listeners = new CopyOnWriteArrayList<>();

    private final Supplier<V> supplier;

    static <V> ListenerCollection<V> create(Supplier<V> supplier) {
        return new ListenerCollection<>(supplier);
    }

    private ListenerCollection(Supplier<V> supplier) {
        this.supplier = notNull(supplier);
    }

    @Override
    public void registerListener(Listener<V> listener) {
        listeners.add(listener);
    }

    @Override
    public void onUpdate() {
        EXECUTOR.execute(this::onUpdateInParallel);
    }

    private void onUpdateInParallel() {
        listeners.forEach(l -> EXECUTOR.execute(() -> l.accept(this.supplier))); //may be get price pair every time.
    }
}
