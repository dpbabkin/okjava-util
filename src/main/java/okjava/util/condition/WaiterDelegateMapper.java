package okjava.util.condition;


import java.util.function.Function;

import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 27.01.2022 - 22:43.
 */
final class WaiterDelegateMapper<V1, V2> implements Waiter<V2> {

    private final Waiter<V1> delegate;
    private final Function<V1, V2> mapper;

    private WaiterDelegateMapper(Waiter<V1> delegate, Function<V1, V2> mapper) {
        this.delegate = notNull(delegate);
        this.mapper = notNull(mapper);
    }

    static <V1, V2> Waiter<V2> create(Waiter<V1> delegate, Function<V1, V2> mapper) {
        return new WaiterDelegateMapper<>(delegate, mapper);
    }

    @Override
    public V2 await(long time) throws InterruptedException {
        return mapper.apply(delegate.await(time));
    }

    @Override
    public void cancel() {
        delegate.cancel();
    }

    @Override
    public void onUpdate() {
        delegate.onUpdate();
    }

    @Override
    public Waiter<V2> withPoll(long pollInterval) {
        return new WaiterDelegateMapper<>(delegate.withPoll(pollInterval), mapper);
    }
}
