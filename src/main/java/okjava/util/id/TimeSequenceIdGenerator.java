package okjava.util.id;

import okjava.util.id.timesequence.TimeSequenceId;
import okjava.util.logger.LoggerUtils;
import okjava.util.string.ToStringBuffer;
import org.slf4j.Logger;

import java.util.concurrent.atomic.AtomicReference;

import static java.lang.System.currentTimeMillis;
import static okjava.util.check.Once.calledOnce;
import static okjava.util.id.timesequence.TimeSequenceIdFactory.timeSequenceIdFactory;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 20:25.
 */
class TimeSequenceIdGenerator implements IdGenerator<TimeSequenceId> {

    private static final int REPEAT_COUNT_LIMIT = 1_000_000;
    private static final int REPEAT_COUNT_THRESHOLD = 10_000;
    private static final Logger LOGGER = LoggerUtils.createLogger(TimeSequenceIdGenerator.class);

    private final static IdGenerator<TimeSequenceId> INSTANCE;

    private final AtomicReference<TimeSequenceId> lastReturnedTimeSequenceId = new AtomicReference<>(timeSequenceIdFactory().create(currentTimeMillis(), 0));

    static {
        INSTANCE = new TimeSequenceIdGenerator();
        try {
            Thread.sleep(1);
            //while (INSTANCE.)
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

    public TimeSequenceId generate() {
        int repeatCount = 0;
        try {
            for (; ; ) {

                final TimeSequenceId oldValue = lastReturnedTimeSequenceId.get();
                long time = currentTimeMillis();
                assert oldValue.getTime() <= time : ToStringBuffer.string("oldValue.getTime() <= time").add("oldValue", oldValue).add("time", time).toString();
                long sequence = time > oldValue.getTime() ? 0 : oldValue.getSequence() + 1;
                TimeSequenceId resultValue = timeSequenceIdFactory().create(time, sequence);
                if (this.lastReturnedTimeSequenceId.compareAndSet(oldValue, resultValue)) {
                    return resultValue;
                }

                if (++repeatCount >= REPEAT_COUNT_LIMIT) {
                    throw ToStringBuffer.string("RepeatCount hit limit.").add("repeatCount", repeatCount).add("limit", REPEAT_COUNT_LIMIT).toException(
                            IllegalStateException::new);
                }
            }
        } finally {
            if (repeatCount >= REPEAT_COUNT_THRESHOLD) {
                ToStringBuffer.string("RepeatCount hit threshold.").add("repeatCount", repeatCount).add("threshold", REPEAT_COUNT_THRESHOLD).toError(LOGGER)
                        .assertFalse();
            }
        }
    }
}
