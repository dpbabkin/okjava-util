package okjava.util.condition;

import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface WaiterFactory extends Pollable<WaiterFactory> {

    Updatable getUpdatable();

    Cancellable getCancellable();

    <V> Waiter<V> waiter(Supplier<V> isEventHappened);

    Waiter<Result> waiterBoolean(BooleanSupplier isEventHappened);
    ///<V> Waiter<V> waiterBoolean(Predicate<V> isEventHappened);
}
