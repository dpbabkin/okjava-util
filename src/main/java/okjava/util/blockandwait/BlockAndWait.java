package okjava.util.blockandwait;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public interface BlockAndWait {
    <V> V await(Supplier<V> isEventHappened) throws InterruptedException;

    <V> V await(Supplier<V> isEventHappened, long time, TimeUnit timeUnit) throws InterruptedException;
}
