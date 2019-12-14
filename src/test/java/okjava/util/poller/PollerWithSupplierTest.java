package okjava.util.poller;

import okjava.util.poller.poller.PollerWithSupplier;
import okjava.util.poller.poller.PollerWithSupplierImpl;

import java.util.concurrent.atomic.AtomicReference;

public class PollerWithSupplierTest extends AbstractBasePollerTest<PollerWithSupplier<Long>> {
    private final AtomicReference<Long> reference = new AtomicReference<>(0L);

    private final PollerWithSupplier<Long> poller = PollerWithSupplierImpl.create(reference::get);

    @Override
    PollerWithSupplier<Long> getPoller() {
        return poller;
    }

    @Override
    void setNewValue(long value) {
        reference.set(value);
        poller.onUpdate();
    }
}
