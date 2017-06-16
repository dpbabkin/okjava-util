package okjava.util.concurrent;

import static okjava.util.NotNull.notNull;

import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         11/26/2016
 *         17:13.
 */
class RunnableQueue implements Runnable {

    private final Lock lock = new ReentrantLock();
    private final Queue<? extends Runnable> queue;

    public RunnableQueue(Queue<? extends Runnable> queue) {
        this.queue = notNull(queue);
    }

    @Override
    public void run() {
        int i = 0;
        do {
            if (lock.tryLock()) {
                try {
                    Runnable run = queue.poll();
                    if (run == null) {
                        break;
                    }
                    run.run();
                } catch (RuntimeException e) {
                    assert false : e.getMessage();
                    throw new RunnableQueueRuntimeException("RunnableQueue execution interrupted.", e);
                } finally {
                    lock.unlock();
                }
            } else {
                break;
            }
            if (i++ > 10_000_000) {
                assert false : "live lock";
            }
        }
        while (!queue.isEmpty());
    }
}
