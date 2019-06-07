package okjava.util.condition;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static okjava.util.NotNull.notNull;

import okjava.util.check.MathCheck;

import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/5/2019
 * 09:38.
 */
public final class BlockingWaitForEvent {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private static final Executor EXECUTOR = ForkJoinPool.commonPool();

    private static final long MAX_POLL_INTERVAL = Long.MAX_VALUE;
    private static final long DEFAULT_POLL_INTERVAL = 100;
    private final long pollInterval;
    private final LongSupplier getInfiniteWaitTime;

    private BlockingWaitForEvent(final long pollInterval) {
        this.pollInterval = MathCheck.positive(pollInterval);
        this.getInfiniteWaitTime = () -> pollInterval;
    }

    public static BlockingWaitForEvent create() {
        return new BlockingWaitForEvent(MAX_POLL_INTERVAL);
    }

    public static BlockingWaitForEvent create(long pollInterval) {
        return new BlockingWaitForEvent(pollInterval);
    }

    public static BlockingWaitForEvent createWithPoll() {
        return new BlockingWaitForEvent(DEFAULT_POLL_INTERVAL);
    }

    private final Runnable sendSignalForced = () -> {
        boolean result = sendSignal(true);
        assert result;
    };

    private boolean sendSignal(boolean force) {
        if (force) {
            lock.lock();
        } else if (!lock.tryLock()) {
            return false;
        }
        assert lock.isLocked();
        assert lock.isHeldByCurrentThread();
        try {
            condition.signalAll();
        } finally {
            lock.unlock();
        }
        return true;
    }

    public void update() {
        if (sendSignal(false) == false) {
            EXECUTOR.execute(sendSignalForced);
        }
    }

    public Waiter waiter(Supplier<Boolean> isEventHappened) {
        return new WaiterImpl(isEventHappened);
    }

    private final class WaiterImpl implements Waiter, Supplier<Boolean> {
        private final Supplier<Boolean> isEventHappened;
        private boolean abort = false;
        private final Supplier<Boolean> abortProvider = this::isAborted;

        private WaiterImpl(Supplier<Boolean> isEventHappened) {
            this.isEventHappened = notNull(isEventHappened);
        }

        @Override
        public void abort() {
            lock.lock();
            try {
                abort = true;
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        private boolean isAborted() {
            lock.lock();
            try {
                return abort;
            } finally {
                lock.unlock();
            }
        }

        @Override
        public boolean await() throws InterruptedException {
            return BlockingWaitForEvent.this.await(this.isEventHappened, abortProvider);
        }

        @Override
        public boolean await(long time, TimeUnit timeUnit) throws InterruptedException {
            return BlockingWaitForEvent.this.await(this.isEventHappened, time, timeUnit, abortProvider);
        }

        /**
         * @return true if should stop.
         */
        @Override
        public Boolean get() {
            return abort || isEventHappened.get();
        }
    }

    private boolean await(Supplier<Boolean> isEventHappened, Supplier<Boolean> isAborted) throws InterruptedException {
        return awaitNative(isEventHappened, getInfiniteWaitTime, isAborted);
    }

    private boolean await(Supplier<Boolean> isEventHappened, long time, TimeUnit timeUnit, Supplier<Boolean> isAborted) throws InterruptedException {
        return awaitNative(isEventHappened, createWaitTimeSupplier(timeUnit.toMillis(time)), isAborted);
    }

    private boolean awaitNative(Supplier<Boolean> isEventHappened, LongSupplier needToWaitProvider, Supplier<Boolean> isAborted) throws InterruptedException {
        for (; ; ) {
            lock.lock();
            try {
                if (isEventHappened.get()) {
                    return true;
                }
                if (isAborted.get() == true) {
                    return false;
                }

                long needToWait = needToWaitProvider.getAsLong();
                assert needToWait >= 0 : needToWait;
                assert needToWait <= pollInterval : needToWait;
                if (needToWait == 0) {
                    return false; //run out of time.
                }
                if (needToWait == MAX_POLL_INTERVAL) {
                    condition.await();
                } else {
                    condition.await(needToWait, TimeUnit.MILLISECONDS);
                }
            } finally {
                lock.unlock();
            }
        }
    }

    private LongSupplier createWaitTimeSupplier(long requestedWaitTime) {
        final long startTime = System.currentTimeMillis();
        return () -> getElapsedTime(startTime, requestedWaitTime);
    }

    private long getElapsedTime(long startTime, long requestedWaitTime) {
        long timeElapsed = (System.currentTimeMillis() - startTime);
        long needToWait = requestedWaitTime - timeElapsed;
        // return value in [0, pollInterval]
        // zero means run out of time.
        return max(0, min(needToWait, pollInterval));
    }
}
