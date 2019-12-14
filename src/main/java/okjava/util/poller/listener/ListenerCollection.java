package okjava.util.poller.listener;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/26/2019
 * 22:04.
 */
public interface ListenerCollection<V> {

    void registerListener(Listener<V> listener);
}
