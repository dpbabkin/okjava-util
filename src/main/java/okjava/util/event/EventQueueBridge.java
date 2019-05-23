package okjava.util.event;

import static okjava.util.NotNull.notNull;

import okjava.util.logger.LoggerUtils;
import okjava.util.string.ToStringBuffer;
import org.slf4j.Logger;

import java.util.function.Consumer;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/23/2019
 * 11:04.
 */
public class EventQueueBridge<E> {

    private final Consumer<E> eventConsumer;
    private final EventQueue<E> eventQueue;
    private final Thread pollerThread;
    private final Logger LOGGER;

    public static <E> EventQueueBridge<E> create(Consumer<E> eventConsumer, EventQueue<E> eventQueue) {
        return new EventQueueBridge<>(eventConsumer, eventQueue);
    }

    private EventQueueBridge(Consumer<E> eventConsumer, EventQueue<E> eventQueue) {

        this.LOGGER = LoggerUtils.createLoggerWithPrefix(EventQueueBridge.class, eventConsumer.toString());

        notNull(eventConsumer);
        this.eventConsumer = event -> {
            LOGGER.info(ToStringBuffer.create("consuming event").add("event", event).toString());
            eventConsumer.accept(event);
        };

        this.eventQueue = notNull(eventQueue);
        assert eventQueue.isActive() == false : eventQueue;
        this.pollerThread = new Thread(this::pollEventQueue, EventQueueBridge.class.getSimpleName() + "._." + eventConsumer.toString());
    }

    public void start() {
        pollerThread.start();
        eventQueue.setActive(true);
    }

    public void interruptAndJoin(long time) throws InterruptedException {
        eventQueue.setActive(false);
        pollerThread.interrupt();
        pollerThread.join(time);
        processLeftover();
    }

    private void pollEventQueue() {
        LOGGER.info("starting");
        for (; ; ) {
            try {
                E event = eventQueue.take();
                //LOGGER.info(ToStringBuffer.create("pollEventQueue()").add("event", event).toString());
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
            E event = eventQueue.poll();
            if (event == null) {
                return;
            }
            LOGGER.info(ToStringBuffer.create("processing leftover").add("event", event).toString());
            eventConsumer.accept(event);
        }
    }
}
