package okjava.util.poller.listener;

import java.util.function.Supplier;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/26/2019
 * 22:04.
 */
public interface SupplierListenerCollection<V> extends ListenerCollection<Supplier<V>> {

    void registerListener(SupplierListener<V> listener);

    @Override
    default void registerListener(Listener<Supplier<V>> listener) {
        registerListener(listener::accept);
    }
}
