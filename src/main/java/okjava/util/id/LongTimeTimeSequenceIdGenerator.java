package okjava.util.id;

import okjava.util.logger.LoggerUtils;
import okjava.util.string.ToStringBuffer;
import org.slf4j.Logger;

import java.util.concurrent.atomic.AtomicLong;

import static okjava.util.check.Once.calledOnce;
import static okjava.util.id.timesequence.LongTimeSequenceIdUtils.incrementSequence;
import static okjava.util.id.timesequence.LongTimeSequenceIdUtils.isMaxSequence;
import static okjava.util.id.timesequence.LongTimeSequenceIdUtils.joinTimeAndSequence;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 20:25.
 */
class LongTimeTimeSequenceIdGenerator implements IdGenerator<Long> {
    private static final Logger LOGGER = LoggerUtils.createLogger(LongTimeTimeSequenceIdGenerator.class);

    private static IdGenerator<Long> INSTANCE;

    static {
        INSTANCE = new LongTimeTimeSequenceIdGenerator();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new Error(e.getMessage(), e);
        }
    }

    private final AtomicLong time = new AtomicLong(joinTimeAndSequence(millis(), 0));

    private LongTimeTimeSequenceIdGenerator() {
        calledOnce(this.getClass());
    }

    static IdGenerator<Long> i() {
        return INSTANCE;
    }

    static IdGenerator<Long> timeSequenceIdGenerator() {
        return INSTANCE;
    }

    static IdGenerator<Long> create() {
        return INSTANCE;
    }

    private static long millis() {
        return System.currentTimeMillis();
    }

    public Long generate() {
        long newValue;
        int repeatCount = 0;
        for (; ; ) {
            if (repeatCount++ < 0) {
                ToStringBuffer.string("RepeatCount hit limit").add("repeatCount=", repeatCount).toException(IllegalStateException::new);
            }

            final long millis = millis();
            newValue = joinTimeAndSequence(millis, 0);
            final long oldValue = time.get();
            if (newValue <= oldValue) {
                if (isMaxSequence(oldValue)) {
                    yield();
                    continue;
                }
                newValue = incrementSequence(oldValue);
            }
            if (this.time.compareAndSet(oldValue, newValue)) {
                break;
            }
        }
        if (repeatCount >= 1_000) {
            LOGGER.error(ToStringBuffer.string("RepeatCountReached").add("repeatCount=", repeatCount).toString());
            assert false : ToStringBuffer.string("RepeatCountReached").add("repeatCount=", repeatCount).toString();
        }

        if (repeatCount >= 1_000_000) {
            throw ToStringBuffer.string("RepeatCountReached").add("repeatCount=", repeatCount).toException(IllegalStateException::new);
        }

        return newValue;
    }

    private static void yield() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
