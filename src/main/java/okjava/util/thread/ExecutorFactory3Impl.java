package okjava.util.thread;

import okjava.util.annotation.Singleton;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.ScheduledExecutorService;

import static okjava.util.check.Once.calledOnce;

@Singleton
class ExecutorFactory3Impl implements ExecutorFactory3 {

    private static ExecutorFactory3 INSTANCE = new ExecutorFactory3Impl();

    private final ExceptionHandler exceptionHandler = new ExceptionHandler();

    private ExecutorFactory3Impl() {
        calledOnce(this.getClass());
    }

    public static ExecutorFactory3 create() {
        return INSTANCE;
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

    private static Executor getDefaultPoll() {
        return ForkJoinPool.commonPool();
    }


    @Override
    public Executor createLowPriorityExecutor() {
        return Executors.newSingleThreadExecutor(runnable -> creteThread(runnable, "Low-priority"));
    }

    @Override
    public ScheduledExecutorService createScheduledExecutor() {
        return Executors.newScheduledThreadPool(1, runnable -> creteThread(runnable, "Scheduled"));
    }

    private Thread creteThread(Runnable runnable, String name) {
        Thread thread = new Thread(runnable, name);
        thread.setDaemon(true);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.setUncaughtExceptionHandler(exceptionHandler);
        return thread;
    }
}
