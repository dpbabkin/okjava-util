package okjava.util.poller;

import com.google.common.collect.Queues;
import okjava.util.condition.WaitForCollection;
import org.junit.Test;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class ValueHolderTest {
    private AtomicReference<Long> reference = new AtomicReference<>(0L);
    private UpdatableValueHolder<Long> valueHolder= ValueHolderFactory.create(reference::get);

    @Test
    public void testListener001() throws InterruptedException {

        this.reference = new AtomicReference<>(0L);
        this.valueHolder= UpdatableValueHolderImpl.create(reference::get);

        WaitForCollection<Long, Queue<Long>> waitForCollection = WaitForCollection.createWithConcurrentLinkedQueue();
        valueHolder.getSupplierListenerCollection().registerListener(longSupplier -> waitForCollection.add(longSupplier.get()));

        setNewValue(1L);
        waitForCollection.getCollectionWaiters().createSizeEqualWaiter(1).second().assertTrue();
        assertThat(waitForCollection.getCopyOfCollection(), contains(1L));

        setNewValue(3L);
        waitForCollection.getCollectionWaiters().createSizeEqualWaiter(2).second().assertTrue();
        assertThat(waitForCollection.getCopyOfCollection(), contains(1L, 3L));

        setNewValue(5L);
        waitForCollection.getCollectionWaiters().createSizeEqualWaiter(3).second().assertTrue();
        assertThat(waitForCollection.getCopyOfCollection(), contains(1L, 3L, 5L));
    }

    private void setNewValue(Long value){
        reference.set(value);
        valueHolder.onUpdate();
    }
}
