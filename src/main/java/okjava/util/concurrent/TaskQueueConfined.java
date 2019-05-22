package okjava.util.concurrent;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 11/25/2016
 * 19:53.
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
