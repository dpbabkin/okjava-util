package okjava.util.blockandwait.general;

import okjava.util.thread.ExecutorFactory;

import java.util.concurrent.Executor;
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
public final class BlockAndWaitGeneralImpl implements BlockAndWaitGeneralUpdatable {

    public static final long WAIT_FOREVER = Long.MAX_VALUE;
    public static final long NO_NEED_TO_WAIT = 0L;
    private static final Executor EXECUTOR = ExecutorFactory.getInstance().getExecutor();

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private final Runnable sendSignalForced = () -> {
        boolean result = sendSignal(true);
        assert result;
    };

    private BlockAndWaitGeneralImpl() {
    }

    public static BlockAndWaitGeneralUpdatable create() {
        return new BlockAndWaitGeneralImpl();
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

    @Override
    public <V> V await(Supplier<V> isEventHappened, LongSupplier needToWaitProvider) throws InterruptedException {
        for (; ; ) {
            lock.lock();
            try {
                V value = isEventHappened.get();
                if (value != null) {
                    return value;
                }

                long needToWait = needToWaitProvider.getAsLong();
                assert needToWait >= 0L : needToWait;
                if (needToWait == NO_NEED_TO_WAIT) {
                    return null; //run out of time or aborted.
                }
                if (needToWait == WAIT_FOREVER) {
                    condition.await();
                } else {
                    condition.await(needToWait, TimeUnit.MILLISECONDS);
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
