package okjava.util.blockandwait;

import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;

public interface WaitTimeSupplierFactory {

    LongSupplier infinite();

    LongSupplier timed(long time);

    default LongSupplier timed(long time, TimeUnit timeUnit) {
        return timed(timeUnit.toMillis(time));
    }
}
