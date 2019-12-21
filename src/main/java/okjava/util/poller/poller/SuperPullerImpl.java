package okjava.util.poller.poller;

import okjava.util.condition.BlockingWaitForEvent;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static okjava.util.NotNull.notNull;

public class SuperPullerImpl<V> implements SuperPuller<V> {

    private final Supplier<V> supplier;
    private final BlockingWaitForEvent blockingWaitForEvent = BlockingWaitForEvent.create();

    private SuperPullerImpl(Supplier<V> supplier) {
        this.supplier = notNull(supplier);
    }

    public static <V> SuperPuller<V> create(Supplier<V> supplier) {
        return new SuperPullerImpl<>(supplier);
    }

    @Override
    public V poll(Predicate<V> tester) throws InterruptedException {
        blockingWaitForEvent.waiter(() -> tester.test(get())).await();
        V value = get();
        return value;
    }

    @Override
    public Optional<V> poll(Predicate<V> tester, long time, TimeUnit timeUnit) throws InterruptedException {
        if (blockingWaitForEvent.waiter(() -> tester.test(get())).await(time, timeUnit).get()) {
            V value = get();
            return Optional.of(value);
        }
        return Optional.empty();
    }

    @Override
    public V get() {
        return supplier.get();
    }

    @Override
    public void onUpdate() {
        blockingWaitForEvent.onUpdate();
    }
}
