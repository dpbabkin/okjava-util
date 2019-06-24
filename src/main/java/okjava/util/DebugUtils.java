package okjava.util;

import okjava.util.annotation.Singleton;

import static okjava.util.check.Once.calledOnce;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 9/15/2015
 * 23:46.
 */
@Singleton
public final class DebugUtils {

    public static final long DEFAULT_SLEEP = 3_000L;

    public static final String DEBUG_PROPERTY_NAME = "okjava.debug.enabled";
    private static final boolean isDebugEnabled = "true".equalsIgnoreCase(System.getProperty(DEBUG_PROPERTY_NAME));

    private static final DebugUtils INSTANCE = new DebugUtils();

    private DebugUtils() {
        calledOnce(this.getClass());
    }

    public static DebugUtils create() {
        return INSTANCE;
    }

    public boolean isDebugEnabled() {
        return isDebugEnabled;
    }

    public void sleep() {
        sleep(this, DEFAULT_SLEEP);
    }

    public void sleep(Object target) {
        sleep(target, DEFAULT_SLEEP);
    }

    public void sleep(long time) {
        sleep(this, time);
    }

    public void sleep(Object target, long time) {
        if (!isDebugEnabled()) {
            return;
        }
        try {
            System.out.println(target.getClass().getName() + " sleep in thread: " + Thread.currentThread().getName());
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }
}

