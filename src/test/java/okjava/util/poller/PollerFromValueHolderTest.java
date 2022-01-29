package okjava.util.poller;

import okjava.util.poller.poller.Poller;

import java.util.concurrent.atomic.AtomicReference;

public class PollerFromValueHolderTest extends AbstractBasePollerTest<Poller<Long>> {
    private final AtomicReference<Long> reference = new AtomicReference<>(0L);

    private final Poller<Long> poller;
    private final Runnable onUpdate;

    public PollerFromValueHolderTest() {
        UpdatableValueHolder<Long> valueHolder = ValueHolderFactory.create(reference::get);
        this.poller = valueHolder.getPoller();
        this.onUpdate = valueHolder::onUpdate;
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
