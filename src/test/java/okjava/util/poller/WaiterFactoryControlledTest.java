package okjava.util.poller;

import okjava.util.condition.WaiterFactories;
import okjava.util.condition.WaiterFactory;
import org.junit.Test;

import static java.lang.Thread.State.TERMINATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WaiterFactoryControlledTest {

    @Test
    public void test() throws InterruptedException {

        WaiterFactory waiterFactory = WaiterFactories.create().withoutDefaultPoll();
        Runnable runnable = () -> {
            try {
                waiterFactory.waiter(() -> null).await(200);
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
