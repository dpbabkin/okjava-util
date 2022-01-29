package okjava.util.poller;

import okjava.util.Two;
import okjava.util.poller.listener.SupplierListenerCollection;
import okjava.util.poller.poller.Poller;
import okjava.util.thread.ExecutorFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import static okjava.util.NotNull.notNull;

class ConsumerValueHolderImpl<V> implements ConsumerValueHolder<V> {
    private static final Executor EXECUTOR = ExecutorFactory.getInstance().getExecutor();
    private final AtomicReference<V> value;
    private final UpdatableValueHolder<V> valueHolder;
    private final Runnable updateValueHolder;

    private ConsumerValueHolderImpl(V value) {
        this.value = new AtomicReference<>(notNull(value));
        this.valueHolder = ValueHolderFactory.create(this.value::get);
        this.updateValueHolder = this.valueHolder::onUpdate;
    }

    static <V> ConsumerValueHolder<V> create(V value) {
        return new ConsumerValueHolderImpl<>(value);
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
        return value.get();
    }

    @Override
    public V apply(V value) {
        V oldValue = this.value.getAndSet(value);
        EXECUTOR.execute(updateValueHolder);
        return oldValue;
    }

    public Two<V, V> mutate(Function<V, V> mutator) {
        for (; ; ) {
            V oldValue = this.value.get();
            V newValue = mutator.apply(oldValue);
            if (this.value.compareAndSet(oldValue, newValue)) {
                EXECUTOR.execute(updateValueHolder);
                return Two.create(oldValue, newValue);
            }
        }
    }
}
