package okjava.util.thread;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolTest {

    @Test //todo remove that test case.
    public void test002() throws InterruptedException {

        Lock lock = new ReentrantLock();

        AtomicInteger atomicInteger = new AtomicInteger(0);
        ForkJoinPool executor = new ForkJoinPool(0x7fff);
        for (int i = 0; i < 1000; i++) {

            executor.execute(() -> {
                atomicInteger.incrementAndGet();
                try {
                    //lock.lock();
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
        System.out.println(atomicInteger.get());
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
