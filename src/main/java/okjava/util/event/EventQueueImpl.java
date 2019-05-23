package okjava.util.event;

import static okjava.util.NotEmpty.notEmptyString;
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
    private volatile boolean active = false;
    private volatile boolean terminated = false;

    EventQueueImpl(BlockingQueue<E> events) {
        this.events = notNull(events);
    }

    @Override
    public void add(E event) {
        if (active == false) {
            throw new IllegalArgumentException(ToStringBuffer.create("EventQueue unactive").add("event", event).toString());
        }
        if (!events.offer(event)) {
            throw new NeverHappensError("events can not accept element. JVM can not continue with that.");
        }
    }

    @Override
    public E take() throws InterruptedException {
        return events.take();
    }


    @Override
    public E poll() {
        return events.poll();
    }

    @Override
    public String toString() {
        return ToStringBuffer.of(this)
                   .add("active", active)
                   .add("terminated", terminated)
                   .add("events", events)
                   .toString();
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        if (terminated == true) {
            throw new IllegalArgumentException(ToStringBuffer.create("queue is terminated").add("this", this).toString());
        }
        this.active = active;
        if (active == false) {
            terminated = true;
        }
    }
}
