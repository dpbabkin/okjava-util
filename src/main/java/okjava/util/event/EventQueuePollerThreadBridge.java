package okjava.util.event;

import okjava.util.logger.LoggerUtils;
import okjava.util.string.ToStringBuffer;
import org.slf4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/23/2019
 * 11:04.
 */
@Deprecated ///never used as QueueBridgeFactory  and QueueBridgeImpl
class EventQueuePollerThreadBridge<E> {

    private final Consumer<E> eventConsumer;
    private final BlockingQueue<E> eventsQueue;
    private final Thread pollerThread;
    private final Logger LOGGER;

    private EventQueuePollerThreadBridge(Consumer<E> eventConsumer, BlockingQueue<E> eventsQueue) {
        this.LOGGER = LoggerUtils.createLogger(EventQueuePollerThreadBridge.class, eventConsumer);
        notNull(eventConsumer);
        this.eventConsumer = event -> {
            LOGGER.info(ToStringBuffer.string("consuming event").add("event", event).toString());
            eventConsumer.accept(event);
        };
        this.eventsQueue = notNull(eventsQueue);
        this.pollerThread = new Thread(this::pollEventQueue, EventQueuePollerThreadBridge.class.getSimpleName() + " @ " + eventConsumer.toString());
    }

    static <E> EventQueuePollerThreadBridge<E> create(Consumer<E> eventConsumer, BlockingQueue<E> eventsQueue) {
        return new EventQueuePollerThreadBridge<>(eventConsumer, eventsQueue);
    }

    EventQueuePollerThreadBridge<E> start() {
        pollerThread.start();
        return this;
    }

    void interruptAndJoin(long time) throws InterruptedException {
        pollerThread.interrupt();
        pollerThread.join(time);
        processLeftover();
    }

    private void pollEventQueue() {
        LOGGER.info("starting");
        for (; ; ) {
            try {
                E event = eventsQueue.take();
                eventConsumer.accept(event);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        LOGGER.info("stopping");
    }

    private void processLeftover() {
        for (; ; ) {
            E event = eventsQueue.poll();
            if (event == null) {
                return;
            }
            LOGGER.info(ToStringBuffer.string("processing leftover").add("event", event).toString());
            eventConsumer.accept(event);
        }
    }

    @Override
    public String toString() {
        return ToStringBuffer.of(this)
                .add("eventConsumer", eventConsumer.toString())
                .add("pollerThread", pollerThread)
                .add("eventsQueue", eventsQueue)
                .toString();
    }
}
