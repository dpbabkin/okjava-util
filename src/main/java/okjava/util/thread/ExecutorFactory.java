package okjava.util.thread;

import okjava.util.annotation.Singleton;
import okjava.util.clazz.ClassResolver;
import okjava.util.concurrent.ExecutableTaskQueueConfined;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.min;
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

    private static final String POOL_FACTORY_CLASS_NAME = "okjava.util.thread.PoolFactoryInstance";
    private final PoolFactory poolFactory = ClassResolver.resolve(POOL_FACTORY_CLASS_NAME, PoolFactoryImpl::create, PoolFactory.class);

    private final OkExecutor OK_EXECUTOR = OkExecutorImpl.create(this.poolFactory.createExecutor());
    private final OkExecutor LOW_PRIORITY = wrapOK(this.poolFactory.createLowPriorityExecutor());
    private final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = this.poolFactory.createScheduledExecutor();

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

//    public ThreadPoolExecutor createCashing(final String name) {
//        return createCashing(name, LoggerFactory.getLogger(clazz));
//    }
//
//    public ThreadPoolExecutor createCashing(final Logger logger) {
//        return createCashing(clazz.getSimpleName(), logger, clazz);
//    }
//
//    public ThreadPoolExecutor createCashing(final String name, final Logger logger, Class clazz) {
//        ExceptionHandler exceptionHandler = new ExceptionHandler(logger, clazz.getName());
//        DaemonThreadFactory daemonThreadFactory = DaemonThreadFactory.create(name, exceptionHandler);
//        return new ThreadPoolExecutor(getCorePoolSize(), getMaximumPoolSize(), getKeepAliveTime(), TimeUnit.MILLISECONDS,
//                new LinkedBlockingQueue<>(), daemonThreadFactory);
//    }

    public Executor getWrapToStringRunnableExecutor(String toString, Executor delegate) {
        return command -> {
            Runnable newRunnable = RunnableUtils.wrapToString(command, toString);
            delegate.execute(newRunnable);
        };
    }

    public OkExecutor getLowPriority() {
        return LOW_PRIORITY;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return SCHEDULED_EXECUTOR_SERVICE;
    }

    public OkExecutor getTaskQueueConfinedExecutor() {
        return getTaskQueueConfinedExecutor(getExecutor());
    }

    public OkExecutor getTaskQueueConfinedExecutor(Executor executor) {
        return wrapOK(ExecutableTaskQueueConfined.create(executor));
    }

    private static OkExecutor wrapOK(Executor executor) {
        return OkExecutorImpl.create(executor);
    }

    public OkExecutor getExecutor() {
        return OK_EXECUTOR;
    }
}
