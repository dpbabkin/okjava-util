package okjava.util.condition;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/5/2019
 * 15:43.
 */
public class BlockingWaitForEventTest {
//
//    private final BlockWaitForEventHappenedStateless block = BlockWaitForEventHappenedStateless.create();
//    private final BlockWaitForEventHappenedStateless2 block2 = BlockWaitForEventHappenedStateless2.create();
//    private final BlockWaitForEventHappenedStateless3 block3 = BlockWaitForEventHappenedStateless3.create();
//    private final BlockWaitForEventHappenedStateless4 block4 = BlockWaitForEventHappenedStateless4.create();

    //private volatile long counter = 0;
    private volatile boolean fail = false;

    @Before
    public void setUp() {
        fail = false;
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(t.getName());
                e.printStackTrace();
                BlockingWaitForEventTest.this.fail = true;
            }
        });

    }

    @Test
    public void test001_1() throws InterruptedException {
        doTest01(BlockingWaitForEvent::create);
    }

    @Test
    public void test001_2() throws InterruptedException {
        doTest02(BlockingWaitForEvent::create);
    }

    public void doTest01(Supplier<BlockingWaitForEvent> waitLock) throws InterruptedException {
        forkThreads(waitLock.get(), 10, 100, 10);
        forkThreads(waitLock.get(), 10, 100, 10);
        forkThreads(waitLock.get(), 10, 100, 10);
    }

    public void doTest02(Supplier<BlockingWaitForEvent> waitLock) throws InterruptedException {

        for (int i = 1; i < 40; i++) {
            for (int j = 100; j < 140; j++) {
                for (int k = 1; k < 40; k++) {
                    forkThreads(waitLock.get(), i, j, k);
                }
            }
        }
    }

    private void forkThreads(BlockingWaitForEvent waitLock, int numberOfThreads, int everyThreadCount, int numberOfTakingThread) throws InterruptedException {
        final long waitForNumber = numberOfThreads * everyThreadCount;

        final Counter counter = new Counter();
        Waiter waiter = waitLock.waiter(() -> counter.getCount() == waitForNumber);

        List<Thread> puttingThreads = Lists.newArrayList();
        for (int i = 0; i < numberOfThreads; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < everyThreadCount; j++) {
                    counter.makeCount(waitLock);
                }
            }, "putting-" + i);
            puttingThreads.add(thread);
        }

        List<Thread> takingThreads = Lists.newArrayList();
        for (int i = 0; i < numberOfTakingThread; i++) {
            Thread thread = new Thread(() -> {
                try {
                    boolean result = waiter.await();
                    assertThat("numberOfThreads=" + numberOfThreads +
                                   " everyThreadCount=" + everyThreadCount +
                                   " numberOfTakingThread=" + numberOfTakingThread +
                                   " counter=" + counter.getCount()
                        , result, is(true));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "taking-" + i);
            takingThreads.add(thread);

        }
        puttingThreads.forEach(Thread::start);
        takingThreads.forEach(Thread::start);

        Thread tFinisher = new Thread(() -> {
            try {
                for (Thread thread : puttingThreads) {
                    thread.join();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            assertThat(counter.getCount(), is(waitForNumber));
            waiter.abort();
        });

        tFinisher.start();

        for (Thread thread : takingThreads) {
            thread.join();
        }

        assertThat("numberOfThreads=" + numberOfThreads + " everyThreadCount=" + everyThreadCount, fail, is(false));
    }

    private static class Counter {
        private volatile long count = 0;

        private final Lock lock = new ReentrantLock();

        private void makeCount(BlockingWaitForEvent waitLock) {
            lock.lock();
            try {
                count++;
            } finally {
                lock.unlock();
            }
            waitLock.update();
        }

        public long getCount() {
            return count;
        }
    }
}
