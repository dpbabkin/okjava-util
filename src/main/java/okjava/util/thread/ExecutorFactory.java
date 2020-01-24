package okjava.util.thread;

import okjava.util.annotation.Singleton;
import okjava.util.concurrent.ExecutableTaskQueueConfined;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.min;
import static okjava.util.NotNull.notNull;
import static okjava.util.check.Once.calledOnce;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/19/2017
 * 22:37.
 */
@Singleton
public final class ExecutorFactory {
    private static final ExecutorFactory INSTANCE = new ExecutorFactory();

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final long KEEP_ALIVE_TIME = TimeUnit.MINUTES.toMillis(5);
    private static final int MIN_CORE_POOL_SIZE = 3;
    private static final OkExecutor FORK_JOIN_POOL_EXECUTOR = OkExecutorImpl.create(new ExecutorImpl());

    private final static class ExecutorImpl implements Executor {

        @Override
        public void execute(Runnable command) {
            execInForkJoinPoll(command);
        }

        private void execInForkJoinPoll(Runnable runnable) {
            Thread t = Thread.currentThread();
            if (t instanceof ForkJoinWorkerThread) {
                ((ForkJoinWorkerThread) t).getPool().execute(runnable);
            } else {
                ForkJoinPool.commonPool().execute(runnable);
            }
        }
    }

    private static final OkExecutor LOW_PRIORITY = wrapOK(Executors.newSingleThreadExecutor(r -> {
        Thread thread = new Thread(r, "Low-priority");
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.setDaemon(true);
        return thread;
    }));

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

    public OkExecutor getLowPriority() {
        return LOW_PRIORITY;
    }

    public OkExecutor getTaskQueueConfinedExecutor() {
        return getTaskQueueConfinedExecutor(getExecutor());
    }

    public OkExecutor getTaskQueueConfinedExecutor(Executor executor) {
        return wrapOK(ExecutableTaskQueueConfined.create(executor));
    }

    public static OkExecutor wrapOK(Executor executor) {
        return OkExecutorImpl.create(executor);
    }

    public OkExecutor getExecutor() {
        return FORK_JOIN_POOL_EXECUTOR;
    }


    private final static class RecursiveActionImpl extends RecursiveAction {

        private final Runnable runnable;

        private RecursiveActionImpl(Runnable runnable) {
            this.runnable = notNull(runnable);
        }

        @Override
        protected void compute() {
            throw new UnsupportedOperationException("not implemented //code generation template");
        }
    }

}
