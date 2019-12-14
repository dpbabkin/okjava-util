package okjava.util.poller;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/26/2019
 * 22:04.
 */
public interface ListenerRegister<V> extends Updatable {

    void registerListener(Listener<V> listener);
}
