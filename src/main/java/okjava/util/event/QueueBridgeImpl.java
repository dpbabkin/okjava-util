package okjava.util.event;

import okjava.util.NeverHappensError;
import okjava.util.e.EConsumer;
import okjava.util.string.ToStringBuffer;

import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/22/2019
 * 01:10.
 */
@Deprecated ///never used as QueueBridgeFactory
final class QueueBridgeImpl<E> implements QueueBridge<E> {

    private final BlockingQueue<E> eventsQueue;
    private final Object MUTEX = new Object();
    private volatile boolean active = false;
    private final Consumer<E> incoming = this::consumeEvent;
    private EventQueuePollerThreadBridge<E> eventQueuePollerThreadBridge = null;

    private QueueBridgeImpl(BlockingQueue<E> eventsQueue) {
        this.eventsQueue = notNull(eventsQueue);
    }

    static <E> QueueBridge<E> create(BlockingQueue<E> eventsQueue) {
        return new QueueBridgeImpl<>(eventsQueue);
    }

    private void consumeEvent(E event) {
        if (active == false) {
            throw new IllegalArgumentException(ToStringBuffer.string("EventsQueue unactive").add("event", event).toString());
        }
        if (!eventsQueue.offer(event)) {
            throw new NeverHappensError("eventsQueue can not accept element. JVM can not continue with that.");
        }
    }

    @Override
    public Consumer<E> getIncomingConsumer() {
        return incoming;
    }

    @Override
    public EConsumer<Long, InterruptedException> linkDestinationConsumer(Consumer<E> consumer) {
        synchronized (MUTEX) {
            if (this.eventQueuePollerThreadBridge != null) {
                throw new IllegalStateException("already been activated");
            }
        }
        this.eventQueuePollerThreadBridge = EventQueuePollerThreadBridge.create(consumer, eventsQueue).start();
        active = true;
        return EConsumer.wrapToString(this::stopAndWait, this::toString);
    }

    private void stopAndWait(long time) throws InterruptedException {
        synchronized (MUTEX) {
            if (this.eventQueuePollerThreadBridge == null) {
                throw new IllegalStateException("had not been activated");
            }
            active = false;
            this.eventQueuePollerThreadBridge.interruptAndJoin(time);
        }
    }


    @Override
    public String toString() {
        return ToStringBuffer.of(this)
                .add("active", active)
                .add("eventsQueue", eventsQueue)
                .add("eventQueuePollerThreadBridge", eventQueuePollerThreadBridge)
                .toString();
    }

}
