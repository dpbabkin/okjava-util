package okjava.util.id;

import okjava.util.id.timesequence.TimeSequenceId;
import okjava.util.logger.LoggerUtils;
import okjava.util.string.ToStringBuffer;
import org.slf4j.Logger;

import java.util.concurrent.atomic.AtomicReference;

import static okjava.util.check.Once.calledOnce;
import static okjava.util.id.timesequence.TimeSequenceIdFactory.timeSequenceIdFactory;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 20:25.
 */
class TimeSequenceIdGenerator implements IdGenerator<TimeSequenceId> {
    private static final Logger LOGGER = LoggerUtils.createLogger(TimeSequenceIdGenerator.class);

    private final static IdGenerator<TimeSequenceId> INSTANCE;

    private final AtomicReference<TimeSequenceId> timeSequenceId = new AtomicReference<>(timeSequenceIdFactory().create(millis(), 0));

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
        int repeatCount = 0;
        try {
            for (; ; ) {

                final TimeSequenceId oldValue = timeSequenceId.get();
                long time = millis();
                assert oldValue.getTime() <= time : ToStringBuffer.string("oldValue.getTime() <= time").add("oldValue", oldValue).add("time", time).toString();
                long sequence = time > oldValue.getTime() ? 0 : oldValue.getSequence() + 1;
                TimeSequenceId resultValue = timeSequenceIdFactory().create(time, sequence);
                if (this.timeSequenceId.compareAndSet(oldValue, resultValue)) {
                    return resultValue;
                }

                if (++repeatCount >= 1_000_000) {
                    throw ToStringBuffer.string("RepeatCount hit limit.").add("repeatCount", repeatCount).add("limit", 1_000_000).toException(IllegalStateException::new);
                }
            }
        } finally {
            if (repeatCount >= 10_000) {
                ToStringBuffer.string("RepeatCount hit threshold.").add("repeatCount", repeatCount).add("threshold", 10_000).toError(LOGGER)
                        .assertFalse();
            }
        }
    }
}
