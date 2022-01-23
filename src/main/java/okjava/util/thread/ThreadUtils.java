package okjava.util.thread;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

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

    public static void sleep(long time, Consumer<InterruptedException> handler) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            handler.accept(e);
        }
    }
}
