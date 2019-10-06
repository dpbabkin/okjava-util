package okjava.util.thread;

import static java.lang.Math.min;
import static okjava.util.check.Once.calledOnce;

import okjava.util.RunnableUtils;
import okjava.util.annotation.Singleton;
import okjava.util.concurrent.ExecutableTaskQueueConfined;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/19/2017
 * 22:37.
 */
@Singleton
public  final class ExecutorFactory {
    private static final ExecutorFactory INSTANCE = new ExecutorFactory();
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final long KEEP_ALIVE_TIME = TimeUnit.MINUTES.toMillis(5);
    private static final int MIN_CORE_POOL_SIZE = 3;
    private static final Executor FORK_JOIN_POOL_EXECUTOR = ForkJoinPool.commonPool();


    private ExecutorFactory() {
        calledOnce(this.getClass());
    }

    public static ExecutorFactory getInstance() {
        return INSTANCE;
    }

    private static int getCorePoolSize() {
        return min(CPU_COUNT + 1, MIN_CORE_POOL_SIZE);
    }

    private static int getMaximumPoolSize() {
        return CPU_COUNT * 10;
    }

    private static long getKeepAliveTime() {
        return KEEP_ALIVE_TIME;
    }

    public ThreadPoolExecutor createCashing(final String name, Class clazz) {
        return createCashing(name, LoggerFactory.getLogger(clazz), clazz);
    }

    public ThreadPoolExecutor createCashing(final Logger logger, Class clazz) {
        return createCashing(clazz.getSimpleName(), logger, clazz);
    }

    public ThreadPoolExecutor createCashing(final String name, final Logger logger, Class clazz) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(logger, clazz.getName());

        DaemonThreadFactory daemonThreadFactory = DaemonThreadFactory.create(name, exceptionHandler);

        return new ThreadPoolExecutor(getCorePoolSize(), getMaximumPoolSize(), getKeepAliveTime(), TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(), daemonThreadFactory);
    }

    public Executor getWrapToStringRunnableExecutor(String toString, Executor delegate) {
        return command -> {
            Runnable newRunnable = RunnableUtils.wrapToString(command, toString);
            delegate.execute(newRunnable);
        };
    }

    public Executor getTaskQueueConfinedExecutor() {
        return ExecutableTaskQueueConfined.create(getExecutor());
    }

    public Executor getExecutor() {
        return FORK_JOIN_POOL_EXECUTOR;
    }
}
