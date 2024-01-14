package okjava.util.condition;


import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 27.01.2022 - 22:43.
 */
class WaiterDelegate<V> implements Waiter<V> {

    private final Waiter<V> delegate;

    WaiterDelegate(Waiter<V> delegate) {
        this.delegate = notNull(delegate);
    }

    @Override
    public V await(long time) throws InterruptedException {
        return delegate.await(time);
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
    public Waiter<V> withPoll(long pollInterval) {
        return new WaiterDelegate<>(delegate.withPoll(pollInterval));
    }
}
