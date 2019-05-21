package okjava.util.thread;

import static okjava.util.empty.EmptyConsumer.emptyConsumer;

import okjava.util.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/19/2017
 * 22:44.
 */
public final class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    private final static Thread.UncaughtExceptionHandler DEFAULT_UNCAUGHT_EXCEPTION_HANDLER = (t, e) -> {
        Stream.of(System.err, System.out).forEach(ps -> {
            ps.println("DEFAULT_UNCAUGHT_EXCEPTION_HANDLER:");
            e.printStackTrace(ps);
        });
    };
    private final Logger logger;
    private final Consumer<String> callback;
    private final String className;
    private final Thread.UncaughtExceptionHandler delegate;

    public ExceptionHandler(Class<?> clazz) {
        this(LoggerFactory.getLogger(clazz), emptyConsumer(), clazz.getName(), DEFAULT_UNCAUGHT_EXCEPTION_HANDLER);
    }

    public ExceptionHandler(Logger logger, String className) {
        this(logger, logger::error, className, DEFAULT_UNCAUGHT_EXCEPTION_HANDLER);
    }

    public ExceptionHandler(Logger logger, Consumer<String> callback, String className) {
        this(logger, callback, className, DEFAULT_UNCAUGHT_EXCEPTION_HANDLER);
    }

    public ExceptionHandler(Logger logger, Consumer<String> callback, String className, Thread.UncaughtExceptionHandler delegate) {
        this.logger = NotNull.notNull(logger);
        this.callback = NotNull.notNull(callback);
        this.className = NotNull.notNull(className);
        this.delegate = NotNull.notNull(delegate);
    }

    private static String systemExit(Logger logger) {
        try {
            String message = ExceptionHandler.class.getName() + ".systemExit()";
            message += Snail.getSnail0();
            Exception e = new Exception(message);
            logger.error(message, e);
            e.printStackTrace(System.err);
            e.printStackTrace(System.out);
            Thread.sleep(903);//wait for logs flushed.
        } finally {
            System.exit(903);
            return "bye";
        }
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        logger.error("Ohh... my god... unbelievable... exception in " + className + " executor thread: " + t.getName(), e);
        try {
            delegate.uncaughtException(t, e);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            try {
                callback.accept("CRITICAL Exception: " + e.getMessage());
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            } finally {
                assert false : systemExit(logger);
            }
        }
    }
}
