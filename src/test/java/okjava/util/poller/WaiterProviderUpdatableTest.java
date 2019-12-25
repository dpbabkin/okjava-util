package okjava.util.poller;

import okjava.util.blockandwait.supplier.WaitTimeSupplierFactory;
import okjava.util.condition.BlockingWaitForEvent;
import okjava.util.condition.waiter.WaiterProviderImpl;
import okjava.util.condition.waiter.WaiterProviderUpdatable;
import org.junit.Test;

import static java.lang.Thread.State.TERMINATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WaiterProviderUpdatableTest {

    @Test
    public void test() throws InterruptedException {

        WaiterProviderUpdatable waiterProviderUpdatable = WaiterProviderImpl.create(WaitTimeSupplierFactory.createWithDefaultPoll());
        Runnable runnable = () -> {
            try {
                waiterProviderUpdatable.waiter(() -> null).await(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        thread.join(1_000);
        assertThat(thread.getState(), is(TERMINATED));
    }
}
