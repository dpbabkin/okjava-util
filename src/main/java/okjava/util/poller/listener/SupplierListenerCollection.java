package okjava.util.poller.listener;

import java.util.function.Supplier;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/26/2019
 * 22:04.
 */
public interface SupplierListenerCollection<V> extends ListenerCollection<Supplier<V>> {

    /**
     * @param listener
     * @return unsubscribe callBack
     */
    Runnable registerListener(SupplierListener<V> listener);

    @Override
    default Runnable registerListener(Listener<Supplier<V>> listener) {
        SupplierListener<V> supplierListener = listener::accept;
        return registerListener(supplierListener);
    }
}
