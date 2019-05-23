package okjava.util.event;

import static okjava.util.NotNull.notNull;

import okjava.util.NeverHappensError;
import okjava.util.e.ERunnable;
import okjava.util.string.ToStringBuffer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/22/2019
 * 01:10.
 */
final class MainQueueImpl<E> implements MainQueue<E> {

    private final EventQueueImpl<E> eventQueue = new EventQueueImpl<>(new LinkedBlockingQueue<>());
    private final BlockingQueue<E> events= new LinkedBlockingQueue<>();
    //private final BlockingQueue<E> events;
    private volatile boolean active = false;
    private volatile boolean terminated = false;

//    MainQueueImpl(BlockingQueue<E> events) {
//        this.events = notNull(events);
//    }


//
//    @Override
//    public boolean isActive() {
//        return active;
//    }
//
//    @Override
//    public void setActive(boolean active) {
//        if (terminated == true) {
//            throw new IllegalArgumentException(ToStringBuffer.create("queue is terminated").add("this", this).toString());
//        }
//        this.active = active;
//        if (active == false) {
//            terminated = true;
//        }
//    }


    private void consumeEvent(E event) {
        if (active == false) {
            throw new IllegalArgumentException(ToStringBuffer.create("EventQueue unactive").add("event", event).toString());
        }
        if (!events.offer(event)) {
            throw new NeverHappensError("events can not accept element. JVM can not continue with that.");
        }
    }

    private final Consumer<E> incoming = this::consumeEvent;


    @Override
    public Consumer<E> getIncomingConsumer() {
        return incoming;
    }

    @Override
    public ERunnable<InterruptedException> linkDestinationConsumer(Consumer<E> consumer) {

        EventQueueImpl<E> queue = new EventQueueImpl<>(events);
        EventQueueBridge<E> queueBridge = EventQueueBridge.create(consumer, queue);
        active=true;
        return ()->queueBridge.interruptAndJoin(10_000);
    }
}
