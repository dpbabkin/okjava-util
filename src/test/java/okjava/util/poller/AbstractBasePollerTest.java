package okjava.util.poller;

import com.google.common.collect.Queues;
import okjava.util.condition.BlockingWaitForEvent;
import okjava.util.condition.WaitForCollection;
import okjava.util.poller.poller.Poller;
import org.junit.Test;

import java.util.Queue;

import static java.lang.Thread.State.WAITING;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

abstract class AbstractBasePollerTest<P extends Poller<Long>> {

    abstract P getPoller();

    abstract void setNewValue(long value);

    @Test
    public void testValueChanged01() throws InterruptedException {
        P poller = getPoller();
        setNewValue(1L);
        PollerTestUtils.assertValue(poller, 1L);

    }

    @Test
    public void testValueChanged02() throws InterruptedException {
        P poller = getPoller();
        PollerTestUtils.assertValue(poller, 0L);
        Thread thread = new Thread(() -> {
            setNewValue(1L);
        });
        thread.start();
        PollerTestUtils.assertValue(poller, 1L);
    }

    @Test
    public void testValueChanged03() throws InterruptedException {
        P poller = getPoller();
        PollerTestUtils.assertValue(poller, 0L);

        BlockingWaitForEvent block = BlockingWaitForEvent.createWithDefaultPoll();
        Thread thread = new Thread(() -> {
            try {
                poller.poll();
                PollerTestUtils.assertValue(poller, 1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });
        thread.start();

        block.waiter(() -> thread.getState() == WAITING).second().assertTrue();
        setNewValue(1L);
        block.waiter(() -> thread.isAlive() == false).second().assertTrue();
        PollerTestUtils.assertValue(poller, 1L);
    }

    @Test
    public void test03() throws InterruptedException {

        P poller = getPoller();
        BlockingWaitForEvent block = BlockingWaitForEvent.createWithDefaultPoll();
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

        setNewValue(1L);
        waitForCollection.createWaiter(1).second().assertTrue();
        PollerTestUtils.assertValue(poller, 1L);
        assertThat(waitForCollection.getCollection(), contains(1L));

        setNewValue(3L);
        waitForCollection.createWaiter(2).second().assertTrue();
        PollerTestUtils.assertValue(poller, 3L);
        assertThat(waitForCollection.getCollection(), contains(1L, 3L));

        setNewValue(5L);
        waitForCollection.createWaiter(3).second().assertTrue();
        PollerTestUtils.assertValue(poller, 5L);
        assertThat(waitForCollection.getCollection(), contains(1L, 3L, 5L));

        thread.interrupt();
        block.waiter(() -> thread.isAlive() == false).second().assertTrue();
    }
}
