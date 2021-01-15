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
        long time;
        long sequence = 0;
        int repeatCount = 1;
        try {
            for (; ; ) {
                final TimeSequenceId oldValue = timeSequenceId.get();
                sequence = 0;
                time = millis();
                assert oldValue.getTime() <= time;
                if (time == oldValue.getTime() && sequence <= oldValue.getSequence()) {
                    sequence = oldValue.getSequence() + 1;
                }
                TimeSequenceId value = timeSequenceIdFactory().create(time, sequence);
                if (this.timeSequenceId.compareAndSet(oldValue, value)) {
                    return value;
                }

                if (repeatCount++ < 0) {
                    ToStringBuffer.string("RepeatCount hit limit").add("repeatCount=", repeatCount).toException(IllegalStateException::new);
                }
            }
        } finally {
            if (repeatCount >= 10_000) {
                LOGGER.error(ToStringBuffer.string("RepeatCountReached").add("repeatCount=", repeatCount).toString());
                assert false : ToStringBuffer.string("RepeatCountReached").add("repeatCount=", repeatCount).toString();
            }

            if (repeatCount >= 1_000_000) {
                throw ToStringBuffer.string("RepeatCountReached").add("repeatCount=", repeatCount).toException(IllegalStateException::new);
            }
        }
    }
}
