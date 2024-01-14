package okjava.util.condition;


import okjava.util.thread.ThreadUtils;

import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 27.01.2022 - 22:43.
 */
final class NoIEWaiterDelegate<V> implements NoIEWaiter<V> {

    private final Waiter<V> delegate;

    private NoIEWaiterDelegate(Waiter<V> delegate) {
        this.delegate = notNull(delegate);
    }

    static <V> NoIEWaiter<V> create(Waiter<V> delegate) {
        return new NoIEWaiterDelegate<>(delegate);
    }

    @Override
    public Waiter<V> toWaiter() {
        return delegate;
    }

    @Override
    public V await(long time) {
        return ThreadUtils.getInterrupted(() -> delegate.await(time));
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
    public NoIEWaiter<V> withPoll(long pollInterval) {
        return new NoIEWaiterDelegate<>(delegate.withPoll(pollInterval));
    }
}
