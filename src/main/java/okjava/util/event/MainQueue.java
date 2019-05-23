package okjava.util.event;

import okjava.util.e.ERunnable;

import java.util.function.Consumer;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 4/28/2018
 * 10:57.
 */
public interface MainQueue<E> {
    Consumer<E> getIncomingConsumer();
    ERunnable<InterruptedException> linkDestinationConsumer(Consumer<E>  consumer);
}
