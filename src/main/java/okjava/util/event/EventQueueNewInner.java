package okjava.util.event;

import java.util.function.Consumer;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 4/28/2018
 * 10:57.
 */
interface EventQueueNewInner<E> extends Consumer<E> {

    void add(E e);

    E take() throws InterruptedException;

    E poll();

    @Override
    default void accept(E e) {
        add(e);
    }

    boolean isActive(); //todo make its private.

    void setActive(boolean active);
}
