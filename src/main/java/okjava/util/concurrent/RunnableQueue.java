package okjava.util.concurrent;

import okjava.util.AssertUtils;
import okjava.util.string.ToStringBuffer;

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
        int repeatCount = 0;
        do {
            if (lock.tryLock()) {
                try {
                    Runnable run = queue.poll();
                    if (run == null) {
                        break;
                    }
                    run.run();
                } catch (RuntimeException e) {
                    AssertUtils.throwAfterAssert("RuntimeException in RunnableQueue", e, RunnableQueueRuntimeException::new);
                } finally {
                    lock.unlock();
                }
            } else {
                break;
            }
            repeatCount++;
            if (repeatCount >= 10_000_000) {
                throw ToStringBuffer.string("RepeatCountReached").add("repeatCount=", repeatCount).toException(IllegalStateException::new);
            }
            assert repeatCount < 1_000_000 : "live lock : " + repeatCount;
        }
        while (!queue.isEmpty());
    }
}
