package okjava.util.event;

import static okjava.util.NotNull.notNull;
import static okjava.util.check.Once.calledOnce;

import okjava.util.annotation.Singleton;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 4/28/2018
 * 10:59.
 */
@Singleton
public final class EventQueueFactory {

    private static EventQueueFactory INSTANCE = new EventQueueFactory();

    private EventQueueFactory() {
        calledOnce(this.getClass());
    }

    public static EventQueueFactory i() {
        return INSTANCE;
    }

    public static EventQueueFactory eventQueueFactory() {
        return INSTANCE;
    }

    public <E> EventQueue<E> createEventQueue() {
        final BlockingQueue<E> events = new LinkedBlockingQueue<>();
        return new EventQueueImpl<>(events);
    }

}

