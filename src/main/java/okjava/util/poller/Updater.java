package okjava.util.poller;

import java.util.function.Supplier;

public interface Updater<V,P extends Poller<V>> extends Supplier<V> {

    P getGenericPoller();

    ListenerRegister<V> getListenerRegister();
}
