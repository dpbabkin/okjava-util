package okjava.util.concurrent;

import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 11/26/2016
 * 17:13.
 */
class RunnableQueue implements Runnable {

    private final Lock lock = new ReentrantLock();
    private final Queue<? extends Runnable> queue;

    RunnableQueue(Queue<? extends Runnable> queue) {
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
                    proceedAssert(e);
                    throw new RunnableQueueRuntimeException("RunnableQueue execution interrupted.", e);
                } finally {
                    lock.unlock();
                }
            } else {
                break;
            }
            i++;
            assert i < 10_000_000 : "live lock : " + i;
        }
        while (!queue.isEmpty());
    }

    private static void proceedAssert(RuntimeException e) {
        if (isAssertEnabled()) {
            throw new java.lang.AssertionError("RuntimeException in RunnableQueue", e);
        }
    }

    private static boolean isAssertEnabled() {
        try {
            assert false : "fake";
            return false;
        } catch (java.lang.AssertionError assertionError) {
            return true;
        }
    }
}
