package okjava.util.cache;

import okjava.util.annotation.Singleton;
import okjava.util.logger.LoggerUtils;
import okjava.util.string.ToStringBuffer;
import org.slf4j.Logger;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static okjava.util.check.Once.calledOnce;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/4/2017
 * 20:02.
 */
@Singleton
final class ReferenceHolderFinalizer<X> {

    private static final String NAME = ReferenceHolderFinalizer.class.getSimpleName();

    private static final Logger LOGGER = LoggerUtils.createLogger(ReferenceHolderFinalizer.class);
    private static final Object MUTEX = new Object();

    private static ReferenceHolderFinalizer<?> INSTANCE = null;
    private final Executor executor = Executors.newSingleThreadExecutor(ReferenceHolderFinalizer::newThread);
    private final ReferenceQueue<X> referenceQueue = new ReferenceQueue<>();
    private final Map<Reference<?>, Runnable> referenceClearCallBack = new ConcurrentHashMap<>();

    private ReferenceHolderFinalizer() {
        calledOnce(ReferenceHolderFinalizer.class);
        initThread();
    }

    @SuppressWarnings("unchecked")
    static <X> ReferenceHolderFinalizer<X> instance() {
        if (INSTANCE == null) {
            synchronized (MUTEX) {
                if (INSTANCE == null) {
                    INSTANCE = new ReferenceHolderFinalizer();
                }
            }
        }
        return (ReferenceHolderFinalizer<X>) INSTANCE;
    }

    private static Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable, NAME + "-worker");
        thread.setUncaughtExceptionHandler((t, e) -> LOGGER.error(ToStringBuffer.ofClass(ReferenceHolderFinalizer.class)
                .add("e.getMessage()", e.getMessage())
                .addThrowable(e)
                .addThread(t).toString(), e));
        thread.setDaemon(true);
        return thread;
    }

    private void initThread() {
        executor.execute(this::doRun);
    }

    void registerReference(Reference<X> reference, Runnable callBack) {
        LOGGER.trace(NAME + " registered: " + callBack + " value=" + reference.get());
        Runnable oldCallBack = referenceClearCallBack.put(reference, callBack);
        runNullable(oldCallBack);
    }

    ReferenceQueue<? super X> getReferenceQueue() {
        return referenceQueue;
    }

    private void runNullable(Runnable runnable) {
        if (runnable != null) {
            LOGGER.trace(NAME + " finalizing " + runnable);
            runnable.run();
        }
    }

    private void doRun() {
        LOGGER.trace(NAME + " started");
        for (; ; ) {
            try {
                Reference<?> reference = referenceQueue.remove();
                Runnable callBack = referenceClearCallBack.remove(reference);
                runNullable(callBack);
            } catch (InterruptedException e) {
                LOGGER.trace(NAME + " interrupted");
                Thread.currentThread().interrupt();
                break;
            }
        }
        LOGGER.trace(NAME + "finished");
    }
}
