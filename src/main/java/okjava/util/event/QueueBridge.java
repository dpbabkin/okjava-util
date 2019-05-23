package okjava.util.event;

import okjava.util.e.EConsumer;

import java.util.function.Consumer;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 4/28/2018
 * 10:57.
 */
public interface QueueBridge<E> extends Consumer<E> {
    Consumer<E> getIncomingConsumer();

    @Override
    default void accept(E e) {
        getIncomingConsumer().accept(e);
    }

    EConsumer<Long, InterruptedException> linkDestinationConsumer(Consumer<E> consumer);
}
