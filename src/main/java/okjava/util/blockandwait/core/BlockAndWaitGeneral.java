package okjava.util.blockandwait.core;

import java.util.function.LongSupplier;
import java.util.function.Supplier;

public interface BlockAndWaitGeneral {
    <V> V await(Supplier<V> isEventHappened, LongSupplier needToWaitProvider) throws InterruptedException;
}
