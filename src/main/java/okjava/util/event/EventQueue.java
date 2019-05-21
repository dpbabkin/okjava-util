package okjava.util.event;

import okjava.util.e.ESupplier;

import java.util.function.Consumer;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 4/28/2018
 * 10:57.
 */
public interface EventQueue<E> extends ESupplier<E, InterruptedException>, Consumer<E> {

    void add(E e);

    E poll() throws InterruptedException;

    @Override
    default void accept(E e) {
        add(e);
    }

    @Override
    default E get() throws InterruptedException {
        return poll();
    }
}
