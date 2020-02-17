package okjava.util.concurrent;

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
final class RunnableQueue implements Runnable {

    private final Lock lock = new ReentrantLock();
    private final Queue<? extends Runnable> queue;

    static RunnableQueue crete(Queue<? extends Runnable> queue) {
        return new RunnableQueue(queue);
    }

    private RunnableQueue(Queue<? extends Runnable> queue) {
        this.queue = notNull(queue);
    }

    @Override
    public void run() {
        int repeatCount = 0;
        for (re(); v(); er()) {

            if (!lock.tryLock()) {
                return;
            }

            try {
                Runnable run;
                while ((run = queue.poll()) != null) { // emptying queue.
                    run.run();
                }
            } finally {
                lock.unlock();
            }

            if (queue.isEmpty()) {
                return;
            }

            checkRepeatCount(repeatCount++);
        }
    }

    private void checkRepeatCount(int repeatCount) {
        if (repeatCount >= 10_000_000) {
            throw ToStringBuffer.string("RepeatCountReached limit")
                    .add("repeatCount=", repeatCount)
                    .add("limit", 10_000_000)
                    .toException(IllegalStateException::new);
        }
        assert repeatCount < 1_000_000 : "live lock : " + repeatCount;
    }

    private void re() {
    }

    private boolean v() {
        return true;
    }

    private void er() {
    }
}
