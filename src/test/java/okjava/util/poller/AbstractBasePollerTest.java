package okjava.util.poller;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import okjava.util.condition.WaitForCollection;
import okjava.util.condition.WaiterFactories;
import okjava.util.condition.WaiterFactory;
import okjava.util.poller.poller.Poller;
import org.junit.Test;

import java.util.List;
import java.util.Queue;

import static com.google.common.truth.Truth.assertThat;
import static java.lang.Thread.State.WAITING;

abstract class AbstractBasePollerTest<P extends Poller<Long>> {

    abstract P getPoller();

    abstract void setNewValue(long value);

    @Test
    public void testValueChanged01() {
        P poller = getPoller();
        setNewValue(1L);
        PollerTestUtils.waitAssertValue(poller, 1L);

    }

    @Test
    public void testValueChanged02() {
        P poller = getPoller();
        PollerTestUtils.waitAssertValue(poller, 0L);
        Thread thread = new Thread(() -> {
            setNewValue(1L);
        });
        thread.start();
        PollerTestUtils.waitAssertValue(poller, 1L);
    }

    @Test
    public void testValueChanged03() throws InterruptedException {
        P poller = getPoller();
        PollerTestUtils.waitAssertValue(poller, 0L);

        WaiterFactory block = WaiterFactories.withoutDefaultPoll();
        Thread thread = new Thread(() -> {
            try {
                long oldValue = poller.get();
                poller.poll(oldValue);
                PollerTestUtils.waitAssertValue(poller, 1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }, getThreadName());
        thread.start();

        block.waiterBoolean(() -> thread.getState() == WAITING).second().assertTrue();
        setNewValue(1L);
        block.waiterBoolean(() -> thread.isAlive() == false).second().assertTrue();
        PollerTestUtils.waitAssertValue(poller, 1L);
    }

    @Test
    public void test03() throws InterruptedException {

        P poller = getPoller();
        WaiterFactory block = WaiterFactories.withoutDefaultPoll();
        WaitForCollection<Long, Queue<Long>> waitForCollection = WaitForCollection.create(Queues.newConcurrentLinkedQueue());
        Thread thread = new Thread(() -> {
            try {
                long oldValue = poller.get();
                for (; ; ) {
                    Long value = poller.poll(oldValue);
                    oldValue = value;
                    waitForCollection.add(value);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, getThreadName());
        thread.start();
        block.waiterBoolean(() -> thread.getState() == WAITING).second().assertTrue();

        setNewValue(1L);
        waitForCollection.createSizeMoreOrEqualWaiter(1).second().assertTrue();
        PollerTestUtils.waitAssertValue(poller, 1L);
        assertThat(waitForCollection.getCollection()).containsExactly(1L).inOrder();

        setNewValue(3L);
        waitForCollection.createSizeMoreOrEqualWaiter(2).second().assertTrue();
        PollerTestUtils.waitAssertValue(poller, 3L);
        assertThat(waitForCollection.getCollection()).containsExactly(1L, 3L).inOrder();

        setNewValue(5L);
        waitForCollection.createSizeMoreOrEqualWaiter(3).second().assertTrue();
        PollerTestUtils.waitAssertValue(poller, 5L);
        assertThat(waitForCollection.getCollection()).containsExactly(1L, 3L, 5L).inOrder();

        thread.interrupt();
        block.waiterBoolean(() -> thread.isAlive() == false).second().assertTrue();
    }

    private String getThreadName() {
        return "test thread " + AbstractBasePollerTest.this.getClass().getSimpleName();
    }

    @Test
    public void test04() throws InterruptedException {

        P poller = getPoller();
        WaiterFactory block = WaiterFactories.withoutDefaultPoll();
        WaitForCollection<Long, Queue<Long>> waitForCollection = WaitForCollection.create(Queues.newConcurrentLinkedQueue());
        Thread thread = new Thread(() -> {
            try {
                Long lastValue = poller.get();
                for (; ; ) {
                    //make sure everytime expect new value.
                    Long value = poller.poll(lastValue);
                    lastValue = value;
                    waitForCollection.add(value);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, getThreadName());
        thread.start();
        block.waiterBoolean(() -> thread.getState() == WAITING).second().assertTrue();

        List<Long> expectedResult = Lists.newArrayList();

        for (long i = 1; i < 10_000L; i++) {
            setNewValue(i);
            waitForCollection.createSizeMoreOrEqualWaiter((int) i).second().assertTrue("i=" + i);
            PollerTestUtils.waitAssertValue(poller, i);
            expectedResult.add(i);
            assertThat(waitForCollection.getCollection()).containsExactlyElementsIn(expectedResult).inOrder();
        }

        thread.interrupt();
        block.waiterBoolean(() -> thread.isAlive() == false).second().assertTrue();
    }
}
