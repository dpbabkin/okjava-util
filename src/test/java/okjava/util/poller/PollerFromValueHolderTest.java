package okjava.util.poller;

import okjava.util.poller.poller.Poller;
import okjava.util.poller.poller.PollerWithSupplier;

import java.util.concurrent.atomic.AtomicReference;

public class PollerFromValueHolderTest extends AbstractBasePollerTest<Poller<Long>> {
    private final AtomicReference<Long> reference = new AtomicReference<>(0L);

    private final Poller<Long> poller;
    private final Runnable onUpdate;

    public PollerFromValueHolderTest() {
        ValueHolderImpl<Long> valueHolder = ValueHolderImpl.create(reference::get);
        this.poller = valueHolder.getPoller();
        onUpdate = valueHolder;
    }

    @Override
    Poller<Long> getPoller() {
        return poller;
    }

    @Override
    void setNewValue(long value) {
        reference.set(value);
        onUpdate.run();
    }
}
