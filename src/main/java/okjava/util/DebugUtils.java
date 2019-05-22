package okjava.util;

import static okjava.util.check.Once.calledOnce;

import okjava.util.annotation.Singleton;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 9/15/2015
 * 23:46.
 */
@Singleton
public final class DebugUtils {

    public static final long DEFAULT_SLEEP = 3_000L;

    public static final String DEBUG_PROPERTY_NAME = "cs.debug.enabled";
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
        sleep(DEFAULT_SLEEP);
    }

    public void sleep(long time) {
        if (!isDebugEnabled()) {
            return;
        }
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}

