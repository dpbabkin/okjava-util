package okjava.util.condition;

import okjava.util.check.MathCheck;
import okjava.util.poller.Updatable;

import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/5/2019
 * 09:38.
 */
public final class BlockingWaitForEvent_OLD implements Updatable {

    private static final Executor EXECUTOR = ForkJoinPool.commonPool();
    private static final long MAX_POLL_INTERVAL = Long.MAX_VALUE;
    private static final long DEFAULT_POLL_INTERVAL = 10;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private final long pollInterval;
    private final LongSupplier getInfiniteWaitTime;
    private final Runnable sendSignalForced = () -> {
        boolean result = sendSignal(true);
        assert result;
    };

    private BlockingWaitForEvent_OLD(final long pollInterval) {
        this.pollInterval = MathCheck.positive(pollInterval);
        this.getInfiniteWaitTime = () -> pollInterval;
    }

    public static BlockingWaitForEvent_OLD create() {
        return new BlockingWaitForEvent_OLD(MAX_POLL_INTERVAL);
    }

    public static BlockingWaitForEvent_OLD createWithPoll(long pollInterval) {
        return new BlockingWaitForEvent_OLD(pollInterval);
    }

    public static BlockingWaitForEvent_OLD createWithPoll() {
        return new BlockingWaitForEvent_OLD(DEFAULT_POLL_INTERVAL);
    }

    private boolean sendSignal(boolean force) {
        if (force) {
            lock.lock();
        } else if (!lock.tryLock()) {
            return false;
        }
        try {
            assert lock.isLocked();
            assert lock.isHeldByCurrentThread();
            condition.signalAll();
        } finally {
            lock.unlock();
        }
        return true;
    }

    public void onUpdate() {
        if (sendSignal(false) == false) {
            EXECUTOR.execute(sendSignalForced);
        }
    }

    public Waiter waiter(Supplier<Boolean> isEventHappened) {
        return new WaiterImpl(isEventHappened);
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
        // returns value in [0, pollInterval]
        // zero means run out of time.
        return max(0, min(needToWait, pollInterval));
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

        @Override
        public Result await() throws InterruptedException {
            return ResultImpl.result(BlockingWaitForEvent_OLD.this.await(this.isEventHappened, abortProvider));
        }

        @Override
        public Result await(long time, TimeUnit timeUnit) throws InterruptedException {
            return ResultImpl.result(BlockingWaitForEvent_OLD.this.await(this.isEventHappened, time, timeUnit, abortProvider));
        }

        private boolean isAborted() {
            lock.lock();
            try {
                return abort;
            } finally {
                lock.unlock();
            }
        }

        /**
         * @return true if should stop.
         */
        @Override
        public Boolean get() {
            return abort || isEventHappened.get();
        }
    }
}
