package okjava.util.thread;

import okjava.util.id.TimeSequenceIdGeneratorFactory;
import org.junit.Ignore;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolTest {

    //@Test //todo remove that test case.
    @Ignore
    public void test002() throws InterruptedException {

        Lock lock = new ReentrantLock();

        AtomicInteger cc = new AtomicInteger(0);

        AtomicInteger atomicInteger = new AtomicInteger(0);
        //   ForkJoinPool executor = new ForkJoinPool();
        ForkJoinPool executor = new ForkJoinPool(
                Runtime.getRuntime().availableProcessors(),
                new ForkJoinPool.ForkJoinWorkerThreadFactory() {
                    @Override
                    public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
                        ForkJoinWorkerThread t = new ForkJoinWorkerThread(pool) {
                        };
                        t.setName("test`" + cc.getAndIncrement() + " " + TimeSequenceIdGeneratorFactory.stringIdGenerator().generate());
                        t.setDaemon(false);
                        return t;
                    }
                },
                new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        throw new UnsupportedOperationException("not implemented //code generation template");
                    }
                }, true);

        ;

        for (int i = 0; i < 100; i++) {

            executor.execute(() -> {
                atomicInteger.incrementAndGet();
                System.out.println(Thread.currentThread().getName());
                try {
                    lock.lock();
                    Thread.sleep(10_000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        System.out.println(executor.getQueuedSubmissionCount());
        System.out.println(executor.getQueuedTaskCount());
        System.out.println(executor.getRunningThreadCount());
        System.out.println(executor.getActiveThreadCount());
        Thread.sleep(2_000);
        System.out.println("");
        System.out.println("atomicInteger=" + atomicInteger.get());
        System.out.println("");
        System.out.println(executor.getQueuedSubmissionCount());
        System.out.println(executor.getQueuedTaskCount());
        System.out.println(executor.getRunningThreadCount());
        System.out.println(executor.getActiveThreadCount());

        Thread.sleep(100_000);
        System.out.println();
        System.out.println(executor.getQueuedSubmissionCount());
        System.out.println(executor.getQueuedTaskCount());
        System.out.println(executor.getRunningThreadCount());
        System.out.println(executor.getActiveThreadCount());

    }
}

