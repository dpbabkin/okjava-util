package okjava.util.condition;

import com.google.common.collect.Lists;
import okjava.util.string.ToStringBuffer;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

import static java.lang.Thread.State.TERMINATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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
    public void test001_0() throws InterruptedException {
        forkThreads(BlockingWaitForEvent.create(), 1, 1, 1);
    }

    @Test
    public void test001_1() throws InterruptedException {
        doTest01(BlockingWaitForEvent::createWithDefaultPoll);
    }

    @Test
    public void test001_2() throws InterruptedException {
        doTest02(BlockingWaitForEvent::create);
    }

    //@Test //takes too long
    public void test001_3() throws InterruptedException {
        doTest03(BlockingWaitForEvent::create);
    }

    public void doTest01(Supplier<BlockingWaitForEvent> waitLock) throws InterruptedException {
        forkThreads(waitLock.get(), 10, 100, 10);
        forkThreads(waitLock.get(), 10, 100, 10);
        forkThreads(waitLock.get(), 10, 100, 10);
        forkThreads(waitLock.get(), 10, 100, 10);
    }

    public void doTest02(Supplier<BlockingWaitForEvent> waitLock) throws InterruptedException {

        for (int i = 1; i < 10; i++) {
            for (int j = 1_000; j < 1_010; j++) {
                for (int k = 1; k < 10; k++) {
                    forkThreads(waitLock.get(), i, j, k);
                }
            }
        }
    }


    public void doTest03(Supplier<BlockingWaitForEvent> waitLock) throws InterruptedException {

        for (int i = 1; i < 40; i++) {
            for (int j = 100; j < 140; j++) {
                for (int k = 1; k < 40; k++) {
                    forkThreads(waitLock.get(), i, j, k);
                }
            }
        }
    }

    @Test
    public void abortTest01() throws InterruptedException {
        abortTest(BlockingWaitForEvent.create());
    }

    private void abortTest(BlockingWaitForEvent waitLock) throws InterruptedException {
        ResultWaiter waiter = waitLock.waiter(() -> false);
        Thread thread = new Thread(() -> {
            try {
                waiter.await(1, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        thread.start();
        waiter.cancel();
        thread.join(1_000);
        assertThat(thread.getState(), is(TERMINATED));
    }

    private void forkThreads(BlockingWaitForEvent waitLock, int numberOfThreads, int everyThreadCount, int numberOfTakingThread) throws InterruptedException {
        System.out.println(ToStringBuffer.string("forkThreads")
                .add("numberOfThreads", numberOfThreads)
                .add("everyThreadCount", everyThreadCount)
                .add("numberOfTakingThread", numberOfTakingThread)
                .addTime()
        );
        final long waitForNumber = numberOfThreads * everyThreadCount;

        //final Counter counter = new Counter();
        final AtomicLong counter = new AtomicLong(0);
        ResultWaiter waiter = waitLock.waiter(() -> counter.get() == waitForNumber);

        List<Thread> puttingThreads = Lists.newArrayList();
        for (int i = 0; i < numberOfThreads; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < everyThreadCount; j++) {

                    counter.incrementAndGet();
                    waitLock.onUpdate();
                    //System.out.println(ToStringBuffer.string("increment count").addThread().add("count", count));
                }
            }, "putting-" + i);
            puttingThreads.add(thread);
        }

        List<Thread> takingThreads = Lists.newArrayList();
        for (int i = 0; i < numberOfTakingThread; i++) {
            Thread thread = new Thread(() -> {
                try {
                    Result result = waiter.await(1, TimeUnit.MINUTES);
                    //System.out.println(ToStringBuffer.string("taking thread finished").add("therad", Thread.currentThread().getName()));
                    result.assertTrue(
                            "numberOfThreads=" + numberOfThreads +
                                    " everyThreadCount=" + everyThreadCount +
                                    " numberOfTakingThread=" + numberOfTakingThread +
                                    " counter=" + counter.get()
                    );

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
            assertThat(counter.get(), is(waitForNumber));
            waiter.cancel();
        }, "finisher thread");

        tFinisher.start();

        for (Thread thread : takingThreads) {
            thread.join();
        }

        assertThat("numberOfThreads=" + numberOfThreads + " everyThreadCount=" + everyThreadCount, fail, is(false));
    }
}
