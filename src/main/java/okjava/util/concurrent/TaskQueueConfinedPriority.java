package okjava.util.concurrent;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 11/25/2016
 * 19:53.
 */
public class TaskQueueConfinedPriority<P> {

    private final PriorityBlockingQueue<PriorityRunnable<P>> queue;
    private final RunnableQueue runnableQueue;

    private TaskQueueConfinedPriority(Comparator<P> comparator) {
        notNull(comparator);
        this.queue = new PriorityBlockingQueue<>(1, (o1, o2) -> comparator.compare(o1.getPriority(), o2.getPriority()));
        this.runnableQueue = RunnableQueue.crete(this.queue);
    }

    public static <P extends Comparable<P>> TaskQueueConfinedPriority<P> create() {
        return create(Comparable::compareTo);
    }

    public static <P> TaskQueueConfinedPriority<P> create(Comparator<P> comparator) {
        return new TaskQueueConfinedPriority<P>(comparator);
    }

    public Runnable queueTask(PriorityRunnable<P> priorityRunnable) {
        queue.add(priorityRunnable);
        return runnableQueue;
    }

    public int getQueueSize() {
        return queue.size();
    }
}
