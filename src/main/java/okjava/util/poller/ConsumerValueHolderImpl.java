package okjava.util.poller;

import okjava.util.poller.listener.SupplierListenerCollection;
import okjava.util.poller.poller.Poller;
import okjava.util.thread.ExecutorFactory;

import java.util.concurrent.Executor;

import static okjava.util.NotNull.notNull;

class ConsumerValueHolderImpl<V> implements ConsumerValueHolder<V> {
    private volatile V value;
    private final UpdatableValueHolder<V> valueHolder;

    private static final Executor EXECUTOR = ExecutorFactory.getInstance().getExecutor();

    static <V> ConsumerValueHolder<V> create(V value) {
        return new ConsumerValueHolderImpl<>(value);
    }

    private ConsumerValueHolderImpl(V value) {
        this.value = notNull(value);
        this.valueHolder = ValueHolderFactory.create(() -> this.value);
    }

    @Override
    public Poller<V> getPoller() {
        return valueHolder.getPoller();
    }

    @Override
    public SupplierListenerCollection<V> getSupplierListenerCollection() {
        return valueHolder.getSupplierListenerCollection();
    }

    @Override
    public V get() {
        return value;
    }

    @Override
    public void accept(V value) {
        this.value = value;
        EXECUTOR.execute(valueHolder);
    }
}
