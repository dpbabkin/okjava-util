package okjava.util.blockandwait.supplier;

import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;

public interface WaitTimeSupplier {

    LongSupplier infinite();

    LongSupplier timed(long time);

    default LongSupplier timed(long time, TimeUnit timeUnit) {
        return timed(timeUnit.toMillis(time));
    }
}
