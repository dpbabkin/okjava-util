package okjava.util.poller;

import java.util.function.Consumer;

public interface UpdaterWithValue<V> extends Updater<V, PollerWithValue<V>>, Consumer<V> {

    ListenerRegister<V> getAllEventListenerRegister();
}
