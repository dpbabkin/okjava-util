package okjava.util.poller.poller;

import okjava.util.thread.ExecutorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/20/2017
 * 17:41.
 */
public class PollerWithSupplierImpl<V> implements PollerWithSupplier<V> {
    //this is good class but I do not see application for that.

    private final static Logger LOGGER = LoggerFactory.getLogger(Poller.class);
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private final Executor executor = ExecutorFactory.getInstance().getExecutor();

    private final Runnable signal = () -> {
        ReentrantLock lock = this.lock;
        lock.lock();
        try {
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    };
    private final Supplier<V> supplier;

    private PollerWithSupplierImpl(Supplier<V> supplier) {
        this.supplier = notNull(supplier);
    }

    public static <V> PollerWithSupplier<V> create(Supplier<V> supplier) {
        return new PollerWithSupplierImpl<>(supplier);
    }

    private void notifyUpdate() {
        ReentrantLock lock = this.lock;
        if (lock.tryLock()) {
            try {
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        } else {
            executor.execute(signal);
        }
    }

    @Override
    public V get() {
        return supplier.get();
    }

    @Override
    public void onUpdate() {
        LOGGER.trace("updated with " + get());
        notifyUpdate();
    }

    @Override
    public V poll(Predicate<V> tester) throws InterruptedException {
        ReentrantLock lock = this.lock;
        V value = get();
        while (tester.test(value) == false) {
            lock.lock();
            try {
                condition.await();
            } finally {
                lock.unlock();
            }
            value = get();
        }
        return value;
    }

    private Optional<V> poll(Predicate<V> tester, Supplier<Long> waitTimeSupplier) throws InterruptedException {
        ReentrantLock lock = this.lock;
        V value = get();
        while (tester.test(value) == false) {
            lock.lock();
            try {
                long waitTime = waitTimeSupplier.get();
                if (waitTime == 0) {
                    condition.await();
                } else {
                    if (!condition.await(waitTimeSupplier.get(), TimeUnit.MILLISECONDS)) {
                        return Optional.empty();
                    }
                }
            } finally {
                lock.unlock();
            }
            value = get();
        }
        return Optional.of(value);
    }

}
