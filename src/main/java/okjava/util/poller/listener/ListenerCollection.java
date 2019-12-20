package okjava.util.poller.listener;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/26/2019
 * 22:04.
 */
public interface ListenerCollection<V> {

    Runnable registerListener(Listener<V> listener);
}
