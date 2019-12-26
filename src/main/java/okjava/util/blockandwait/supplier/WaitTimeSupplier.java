package okjava.util.blockandwait.supplier;

import okjava.util.blockandwait.Constants;

import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;

public interface WaitTimeSupplier {

    default LongSupplier infinite() {
        return timed(Constants.WAIT_FOREVER);
    }

    LongSupplier timed(long time);

    default LongSupplier timed(long time, TimeUnit timeUnit) {
        return timed(timeUnit.toMillis(time));
    }
}
