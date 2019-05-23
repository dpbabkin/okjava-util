package okjava.util.event;

import static okjava.util.check.Never.neverNeverCalled;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 4/28/2018
 * 10:59.
 */
@Utility
public enum QueueBridgeFactory {
    ;

    QueueBridgeFactory(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

//    private static EventQueueFactory INSTANCE = new EventQueueFactory();
//
//    private EventQueueFactory() {
//        calledOnce(this.getClass());
//    }
//
//    public static EventQueueFactory i() {
//        return INSTANCE;
//    }
//
//    public static EventQueueFactory eventQueueFactory() {
//        return INSTANCE;
//    }

    public static <E> QueueBridge<E> createQueueBridge() {
        final BlockingQueue<E> eventQueues = new LinkedBlockingQueue<>();
        return QueueBridgeImpl.create(eventQueues);
    }

}

