package okjava.util.id;

import okjava.util.id.timesequence.TimeSequenceId;
import okjava.util.id.timesequence.TimeSequenceIdFactory;
import okjava.util.logger.LoggerUtils;
import okjava.util.string.ToStringBuffer;
import org.slf4j.Logger;

import java.util.concurrent.atomic.AtomicLong;

import static okjava.util.check.Once.calledOnce;
import static okjava.util.id.LongTimeSequenceIdUtils.incrementSequence;
import static okjava.util.id.LongTimeSequenceIdUtils.isMaxSequence;
import static okjava.util.id.LongTimeSequenceIdUtils.joinTimeAndSequence;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 20:25.
 */
class TimeSequenceIdGenerator implements IdGenerator<TimeSequenceId> {
    private static final Logger LOGGER = LoggerUtils.createLogger(TimeSequenceIdGenerator.class);

    private final static IdGenerator<TimeSequenceId> INSTANCE;

    private final AtomicLong time = new AtomicLong(joinTimeAndSequence(millis(), 0));

    static {
        INSTANCE = new TimeSequenceIdGenerator();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new Error(e.getMessage(), e);
        }
    }

    private TimeSequenceIdGenerator() {
        calledOnce(this.getClass());
    }

    static IdGenerator<TimeSequenceId> timeSequenceIdGenerator() {
        return INSTANCE;
    }

    private static long millis() {
        return System.currentTimeMillis();
    }

    public TimeSequenceId generate() {
        long newIdValue;
        int repeatCount = 1;
        for (; ; ) {
            if (repeatCount++ < 0) {
                ToStringBuffer.string("RepeatCount hit limit").add("repeatCount=", repeatCount).toException(IllegalStateException::new);
            }

            final long millis = millis();
            newIdValue = joinTimeAndSequence(millis, 0);
            final long oldValue = time.get();
            if (newIdValue <= oldValue) {
                if (isMaxSequence(oldValue)) {
                    TimeSequenceIdGenerator.yield();
                    continue;
                }
                newIdValue = incrementSequence(oldValue);
            }
            if (this.time.compareAndSet(oldValue, newIdValue)) {
                break;
            }
        }
        if (repeatCount >= 10_000) {
            LOGGER.error(ToStringBuffer.string("RepeatCountReached").add("repeatCount=", repeatCount).toString());
            assert false : ToStringBuffer.string("RepeatCountReached").add("repeatCount=", repeatCount).toString();
        }

        if (repeatCount >= 1_000_000) {
            throw ToStringBuffer.string("RepeatCountReached").add("repeatCount=", repeatCount).toException(IllegalStateException::new);
        }

        return TimeSequenceIdFactory.timeSequenceIdFactory().fromLong(newIdValue);
    }

    private static void yield() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
