package okjava.util.thread;

import okjava.util.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/19/2017
 * 22:44.
 */
public final class UncaughtExcHandler implements Thread.UncaughtExceptionHandler {

    private final static Thread.UncaughtExceptionHandler SOUT_SERR_UNCAUGHT_EXCEPTION_HANDLER = (t, e) -> {
        Stream.of(System.err, System.out).forEach(ps -> {
            ps.println("DEFAULT_UNCAUGHT_EXCEPTION_HANDLER:");
            e.printStackTrace(ps);
        });
    };
    private final Logger logger;
    private final Thread.UncaughtExceptionHandler delegate;


    private static final UncaughtExcHandler DEFAULT_EXCEPTION_HANDLER = new UncaughtExcHandler(UncaughtExcHandler.class);

    public static Thread.UncaughtExceptionHandler create() {
        return DEFAULT_EXCEPTION_HANDLER;
    }

    public static Thread.UncaughtExceptionHandler createThreadForward() {
        return ThreadForwardUncaughtExceptionHandler.threadForwardUncaughtExceptionHandler();
    }

    private UncaughtExcHandler(Class<?> clazz) {
        this(LoggerFactory.getLogger(clazz), SOUT_SERR_UNCAUGHT_EXCEPTION_HANDLER);
    }

    private UncaughtExcHandler(Logger logger) {
        this(logger, SOUT_SERR_UNCAUGHT_EXCEPTION_HANDLER);
    }

    private UncaughtExcHandler(Logger logger, Thread.UncaughtExceptionHandler delegate) {
        this.logger = NotNull.notNull(logger);
        this.delegate = NotNull.notNull(delegate);
    }

    private static String systemExit(Logger logger) {
        try {
            String message = UncaughtExcHandler.class.getName() + ".systemExit()";
            message += Snail.getSnail0();
            Exception e = new Exception(message);
            logger.error(message, e);
            e.printStackTrace(System.err);
            e.printStackTrace(System.out);
            Thread.sleep(903);//wait for logs flushed.
        } finally {
            System.exit(903);
            return "systemExit";
        }
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        logger.error("Ohh... my god... unbelievable... exception in  executor thread: " + t.getName(), e);
        try {
            delegate.uncaughtException(t, e);
        } catch (Throwable ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            assert false : systemExit(logger);
        }
    }
}
