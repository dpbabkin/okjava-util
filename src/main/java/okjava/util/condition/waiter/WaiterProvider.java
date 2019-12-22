package okjava.util.condition.waiter;

import java.util.function.Supplier;

public interface WaiterProvider {
    <V> Waiter<V> waiter(Supplier<V> isEventHappened);
}
