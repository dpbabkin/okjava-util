package okjava.util.thread;

import okjava.util.annotation.Singleton;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

import static okjava.util.check.Once.calledOnce;

@Singleton
class PoolFactoryImpl implements PoolFactory {

    private final static Thread.UncaughtExceptionHandler exceptionHandler = UncaughtExcHandler.create();
    private static final ThreadFactory MIN_PRIORIT_THREAD_FACTORY = new ThreadFactory() {
        @Override
        public Thread newThread(@NotNull Runnable r) {
            return creteMinPriorityThread(r, "Low-priority");
        }
    };
    private static PoolFactory INSTANCE = new PoolFactoryImpl();

    private PoolFactoryImpl() {
        calledOnce(this.getClass());
    }

    public static PoolFactory create() {
        return INSTANCE;
    }

    private static Executor getDefaultPoll() {
        return ForkJoinPool.commonPool();
    }

    private static Thread creteMinPriorityThread(Runnable runnable, String name) {
        Thread thread = new Thread(runnable, name);
        thread.setDaemon(true);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.setUncaughtExceptionHandler(exceptionHandler);
        return thread;
    }

    @Override
    public Executor createExecutor() {
        Thread t = Thread.currentThread();
        if (t instanceof ForkJoinWorkerThread) {
            return ((ForkJoinWorkerThread) t).getPool();
        } else {
            return getDefaultPoll();
        }
    }

    @Override
    public ScheduledExecutorService createScheduledExecutor() {
        return Executors.newSingleThreadScheduledExecutor(runnable -> creteMinPriorityThread(runnable, "Scheduled"));
    }

    @Override
    public Executor createLowPriorityExecutor() {
        return Executors.newSingleThreadExecutor(MIN_PRIORIT_THREAD_FACTORY);
    }
}
