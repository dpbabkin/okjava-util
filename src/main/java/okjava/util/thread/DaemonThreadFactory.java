package okjava.util.thread;

import static okjava.util.NotNull.notNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/20/2017
 * 18:01.
 */
public final class DaemonThreadFactory implements ThreadFactory {

    private final String threadName;
    private final Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    private final AtomicInteger threadNumber = new AtomicInteger(1);

    private DaemonThreadFactory(String threadName, Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.threadName = notNull(threadName);
        this.uncaughtExceptionHandler = notNull(uncaughtExceptionHandler);
    }

    public static DaemonThreadFactory create(String threadName, Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        return new DaemonThreadFactory(threadName, uncaughtExceptionHandler);
    }

    private String generateThreadName() {
        return threadName + " " + threadNumber.getAndIncrement() + "";
    }

    @Override
    public Thread newThread(Runnable task) {
        Thread thread = new Thread(notNull(task), generateThreadName());
        thread.setDaemon(true);
        thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
        return thread;
    }
}
