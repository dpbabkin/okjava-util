package okjava.util.concurrent;

import static okjava.util.NotNull.notNull;
import static okjava.util.e.RuntimeExceptionHandler.fromConsumer;

import okjava.util.e.RuntimeExceptionHandler;
import okjava.util.empty.EmptyConsumer;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         11/25/2016
 *         19:53.
 */
public class TaskQueueConfined {

    private final LinkedBlockingDeque<Runnable> queue = new LinkedBlockingDeque<>();
    private final RunnableQueue runnableQueue;


    public TaskQueueConfined() {
        this.runnableQueue = new RunnableQueue(queue);
    }

    public Runnable queueTaskLast(Runnable runnable) {
        queue.add(runnable);
        return runnableQueue;
    }

    public Runnable queueTaskFirst(Runnable runnable) {
        queue.addFirst(runnable);
        return runnableQueue;
    }

    public int getQueueSize() {
        return queue.size();
    }
}
