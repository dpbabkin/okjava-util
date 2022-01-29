package okjava.util.blockandwait;

import okjava.util.annotation.Singleton;
import okjava.util.id.timesequence.TimeSequenceId;
import okjava.util.logger.LoggerUtils;
import okjava.util.string.ToStringBuffer;
import okjava.util.thread.ExecutorFactory;
import org.slf4j.Logger;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

import static okjava.util.blockandwait.Constants.NO_NEED_TO_WAIT;
import static okjava.util.blockandwait.Constants.WAIT_FOREVER;
import static okjava.util.check.Once.calledOnce;
import static okjava.util.id.TimeSequenceIdGeneratorFactory.timeSequenceIdGenerator;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/5/2019
 * 09:38.
 */
public final class BlockAndWaitMain implements BlockAndWait {

    private static final Executor EXECUTOR = ExecutorFactory.getInstance().getExecutor();
    private static final Logger LOGGER = LoggerUtils.createLogger(BlockAndWaitMain.class);

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private final Runnable forcedSendSignal = new ForcedSignalSender();
    private volatile boolean cancelled = false;

    private BlockAndWaitMain() {
    }

    public static BlockAndWait create() {
        return new BlockAndWaitMain();
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

    @Override
    public void onUpdate() {
        if (sendSignal(false) == false) {
            EXECUTOR.execute(forcedSendSignal);
        }
    }

    @Override
    public <V> V await(Supplier<V> isEventHappened, long timeToWait) throws InterruptedException {
        final long startTime = System.currentTimeMillis();
        WaitStrategy waitStrategy = createWaitStrategy(timeToWait);

        TimeSequenceId timeSequenceId = null;
        if (LOGGER.isTraceEnabled()) {
            timeSequenceId = timeSequenceIdGenerator().generate();
            ToStringBuffer.string("Start awaiting")
                    .add("waitId", timeSequenceId)
                    .addWithClass("waitStrategy", waitStrategy)
                    .add("startTime", startTime)
                    .toTrace(LOGGER);
        }

        for (; ; ) {
            lock.lock();
            try {
                V value = isEventHappened.get();
                if (value != null || cancelled) {
                    return value;
                }
                if (waitStrategy.await(condition, startTime, timeToWait) == false) {
                    // time elapsed.
                    return isEventHappened.get();
                }

            } finally {
                lock.unlock();
                long endTime = System.currentTimeMillis();
                if (LOGGER.isTraceEnabled()) {
                    ToStringBuffer.string("Stop await")
                            .add("waitId", timeSequenceId)
                            .addWithClass("waitStrategy", waitStrategy)
                            .add("startTime", startTime)
                            .add("endTime", endTime)
                            .add("diff", endTime - startTime)
                            .toTrace(LOGGER);
                }
            }

        }
    }

    private WaitStrategy createWaitStrategy(long timeToWait) {
        if (timeToWait == NO_NEED_TO_WAIT) {
            return ZeroTimeWaitStrategy.create();
        } else if (timeToWait == WAIT_FOREVER) {
            return ForeverWaitStrategy.create();
        } else {
            return EndTimeWaitStrategy.create();
        }
    }

    @Override
    public void cancel() {
        this.cancelled = true;
        onUpdate();
    }

    private interface WaitStrategy {
        /**
         * Returns false if the waiting time detectably elapsed before return from the method, else true
         */
        boolean await(Condition condition, long startTime, long waitTime) throws InterruptedException;
    }

    @Singleton
    private final static class ZeroTimeWaitStrategy implements WaitStrategy {

        private static final WaitStrategy INSTANCE = new ZeroTimeWaitStrategy();
        private static final String TO_STRING = ToStringBuffer.ofClass(ZeroTimeWaitStrategy.class).toString();

        private ZeroTimeWaitStrategy() {
            calledOnce(this.getClass());
        }

        public static WaitStrategy create() {
            return INSTANCE;
        }

        @Override
        public boolean await(Condition condition, long startTime, long waitTime) {
            return false; // time always elapsed.
        }

        @Override
        public String toString() {
            return TO_STRING;
        }
    }


    @Singleton
    private final static class ForeverWaitStrategy implements WaitStrategy {

        private static final WaitStrategy INSTANCE = new ForeverWaitStrategy();
        private static final String TO_STRING = ToStringBuffer.ofClass(ForeverWaitStrategy.class).toString();

        private ForeverWaitStrategy() {
            calledOnce(this.getClass());
        }

        public static WaitStrategy create() {
            return INSTANCE;
        }

        @Override
        public boolean await(Condition condition, long startTime, long waitTime) throws InterruptedException {
            condition.await();
            return true;// time never elapsed.
        }

        @Override
        public String toString() {
            return TO_STRING;
        }
    }

    @Singleton
    private final static class EndTimeWaitStrategy implements WaitStrategy {

        private static final WaitStrategy INSTANCE = new EndTimeWaitStrategy();
        private static final String TO_STRING = ToStringBuffer.ofClass(EndTimeWaitStrategy.class).toString();

        private EndTimeWaitStrategy() {
            calledOnce(this.getClass());
        }

        public static WaitStrategy create() {
            return INSTANCE;
        }

        @Override
        public boolean await(Condition condition, long startTime, long waitTime) throws InterruptedException {
            final long alreadyPassedTime = System.currentTimeMillis() - startTime;
            assert alreadyPassedTime >= 0;
            final long needToWait = waitTime - alreadyPassedTime;
            if (needToWait > 0) {
                return condition.await(needToWait, TimeUnit.MILLISECONDS);
            }
            return false;
        }

        @Override
        public String toString() {
            return TO_STRING;
        }
    }

    private class ForcedSignalSender implements Runnable {
        @Override
        public void run() {
            boolean result = BlockAndWaitMain.this.sendSignal(true);
            assert result;
        }
    }
}
