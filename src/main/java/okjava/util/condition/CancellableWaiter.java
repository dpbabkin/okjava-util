package okjava.util.condition;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 28.01.2022 - 00:34.
 */
public interface CancellableWaiter<V> extends Waiter<V>, Cancellable {
}
