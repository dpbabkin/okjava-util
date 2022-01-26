package okjava.util.blockandwait.core;

import okjava.util.id.timesequence.TimeSequenceId;
import okjava.util.logger.LoggerUtils;
import okjava.util.string.ToStringBuffer;
import okjava.util.thread.ExecutorFactory;
import org.slf4j.Logger;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

import static okjava.util.blockandwait.Constants.NO_NEED_TO_WAIT;
import static okjava.util.blockandwait.Constants.WAIT_FOREVER;
import static okjava.util.id.TimeSequenceIdGeneratorFactory.timeSequenceIdGenerator;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/5/2019
 * 09:38.
 */
public final class BlockAndWaitGeneralImpl implements BlockAndWaitGeneralUpdatable {

    private static final Executor EXECUTOR = ExecutorFactory.getInstance().getExecutor();
    private static final Logger LOGGER = LoggerUtils.createLogger(BlockAndWaitGeneralImpl.class);
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private final Runnable sendSignalForced = new SignalSender();

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
            TimeSequenceId timeSequenceId = timeSequenceIdGenerator().generate();
            timeSequenceId.getCreationInstant().toEpochMilli();
            ToStringBuffer.string("Start await")
                    .add("needToWaitProvider", needToWaitProvider.getAsLong())
                    .add("System.currentTimeMillis()", System.currentTimeMillis())
                    .add("timeSequenceId.getCreationInstant().toEpochMilli()", timeSequenceId.getCreationInstant().toEpochMilli())
                    .add("waitId", timeSequenceId)
                    .toDebug(LOGGER);
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
                ToStringBuffer.string("Stop await")
                        .add("needToWaitProvider", needToWaitProvider.getAsLong())
                        .add("System.currentTimeMillis()", System.currentTimeMillis())
                        .add("timeSequenceId.getCreationInstant().toEpochMilli()", timeSequenceId.getCreationInstant().toEpochMilli())
                        .add("diff", System.currentTimeMillis() - timeSequenceId.getCreationInstant().toEpochMilli())
                        .add("waitId", timeSequenceId)
                        .toDebug(LOGGER);
            }
        }
    }

    private class SignalSender implements Runnable {
        @Override
        public void run() {
            boolean result = BlockAndWaitGeneralImpl.this.sendSignal(true);
            assert result;
        }
    }
}
