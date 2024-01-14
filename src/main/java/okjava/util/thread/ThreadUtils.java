package okjava.util.thread;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;
import okjava.util.e.ERunnable;
import okjava.util.e.ESupplier;

import java.util.function.Consumer;

import static okjava.util.check.Never.neverNeverCalled;


/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/1/2016
 * 21:39.
 */
@Utility
public enum ThreadUtils {
    ;

    ThreadUtils(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static void sleep(long time) {
        sleep(time, ThreadUtils::throwNewRuntimeException);
    }

    private static void throwNewRuntimeException(Exception e) {
        throw new RuntimeException(e);
    }

    public static <R> R sleepForever() throws InterruptedException {
        for (long l = 0; l < Long.MAX_VALUE; l += 0) {
            Thread.sleep(Long.MAX_VALUE);
        }
        throw new IllegalStateException();
    }

    public static <V> V getInterrupted(ESupplier<V, InterruptedException> runnable) {
        try {
            return runnable.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static void runInterrupted(ERunnable<InterruptedException> runnable) {
        getInterrupted(() -> {
            runnable.run();
            return Void.class;
        });
    }

    public static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static void sleep(long time, Consumer<InterruptedException> handler) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            handler.accept(e);
        }
    }
}
