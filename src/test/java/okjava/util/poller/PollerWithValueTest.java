package okjava.util.poller;

import okjava.util.poller.poller.PollerWithValue;
import okjava.util.poller.poller.PollerWithVaueImpl;

public class PollerWithValueTest extends AbstractBasePollerTest<PollerWithValue<Long>> {

    private final PollerWithValue<Long> poller = PollerWithVaueImpl.create(0L);

    @Override
    PollerWithValue<Long> getPoller() {
        return poller;
    }

    @Override
    void setNewValue(long value) {

        poller.accept(value);
    }
}
