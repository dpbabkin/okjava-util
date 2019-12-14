package okjava.util.poller;

import com.google.common.collect.Queues;
import okjava.util.condition.BlockingWaitForEvent;
import okjava.util.condition.WaitForCollection;
import org.junit.Test;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Thread.State.WAITING;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class PollerTest {

    @Test
    public void test01() throws InterruptedException {

        AtomicReference<Long> reference = new AtomicReference<>(0L);
        PollerWithSupplier<Long> poller = PollerWithSupplierImpl.create(reference::get);
        BlockingWaitForEvent block = BlockingWaitForEvent.createWithPoll();
        Thread thread = new Thread(() -> {
            try {
                poller.poll();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });
        thread.start();

        block.waiter(() -> thread.getState() == WAITING).second().assertTrue();
        reference.set(1L);
        poller.onUpdate();
        block.waiter(() -> thread.isAlive() == false).second().assertTrue();
    }

    @Test
    public void test02() throws InterruptedException {

        AtomicReference<Long> reference = new AtomicReference<>(0L);
        PollerWithSupplier<Long> poller = PollerWithSupplierImpl.create(reference::get);
        BlockingWaitForEvent block = BlockingWaitForEvent.createWithPoll();
        Thread thread = new Thread(() -> {
            try {
                poller.poll(0L);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });
        thread.start();
        block.waiter(() -> thread.getState() == WAITING).second().assertTrue();
        reference.set(1L);
        poller.onUpdate();
        block.waiter(() -> thread.isAlive() == false).second().assertTrue();
    }

    @Test
    public void test03() throws InterruptedException {

        AtomicReference<Long> reference = new AtomicReference<>(0L);
        PollerWithSupplier<Long> poller = PollerWithSupplierImpl.create(reference::get);
        BlockingWaitForEvent block = BlockingWaitForEvent.createWithPoll();
        WaitForCollection<Long, Queue<Long>> waitForCollection = WaitForCollection.create(Queues.newConcurrentLinkedQueue());
        Thread thread = new Thread(() -> {
            try {
                for (; ; ) {
                    Long value = poller.poll();
                    waitForCollection.add(value);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        thread.start();
        block.waiter(() -> thread.getState() == WAITING).second().assertTrue();

        reference.set(1L);
        poller.onUpdate();
        waitForCollection.createWaiter(1).second().assertTrue();
        assertThat(waitForCollection.getCollection(), contains(1L));

        reference.set(3L);
        poller.onUpdate();
        waitForCollection.createWaiter(2).second().assertTrue();
        assertThat(waitForCollection.getCollection(), contains(1L, 3L));

        reference.set(5L);
        poller.onUpdate();
        waitForCollection.createWaiter(3).second().assertTrue();
        assertThat(waitForCollection.getCollection(), contains(1L, 3L, 5L));

        thread.interrupt();
        block.waiter(() -> thread.isAlive() == false).second().assertTrue();
    }
}
