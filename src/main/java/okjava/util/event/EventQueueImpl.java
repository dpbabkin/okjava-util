package okjava.util.event;

import static okjava.util.NotNull.notNull;

import okjava.util.NeverHappensError;
import okjava.util.string.ToStringBuffer;

import java.util.concurrent.BlockingQueue;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/22/2019
 * 01:10.
 */
final class EventQueueImpl<E> implements EventQueue<E> {
    private final BlockingQueue<E> events;

    EventQueueImpl(BlockingQueue<E> events) {
        this.events = notNull(events);
    }

    @Override
    public void add(E e) {
        if (!events.offer(e)) {
            throw new NeverHappensError("events can not accept element. JVM can not continue with that.");
        }
    }

    @Override
    public E poll() throws InterruptedException {
        return events.take();
    }

    @Override
    public String toString() {
        return ToStringBuffer.of(this)
                   .add("events", events)
                   .toString();
    }
}
