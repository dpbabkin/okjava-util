package okjava.util.poller;

import okjava.util.poller.listener.SupplierListenerCollection;
import okjava.util.poller.poller.Poller;

import java.util.function.Supplier;

public interface ValueHolder<V> extends Supplier<V> {

    Poller<V> getPoller();

    SupplierListenerCollection<V> getSupplierListenerCollection();
}
