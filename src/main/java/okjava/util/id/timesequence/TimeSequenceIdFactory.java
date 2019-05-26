package okjava.util.id.timesequence;

import static okjava.util.check.Once.calledOnce;
import static okjava.util.id.timesequence.LongTimeSequenceIdUtils.ifUnderLimit;

import okjava.util.annotation.Singleton;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/7/2019
 * 23:12.
 */
@Singleton
public class TimeSequenceIdFactory {

    private static final TimeSequenceIdFactory INSTANCE = new TimeSequenceIdFactory();

    private TimeSequenceIdFactory() {
        calledOnce(this.getClass());
    }

    public static TimeSequenceIdFactory i() {
        return INSTANCE;
    }

    public static TimeSequenceIdFactory timeSequenceIdFactory() {
        return INSTANCE;
    }

    public TimeSequenceId fromLong(long id) {
        return LongTimeSequenceId.fromLong(id);
    }

    public TimeSequenceId create(long time, long sequence) {
        if (ifUnderLimit(time, sequence)) {
            return LongTimeSequenceId.create(time, sequence);
        }
        return TimeSequenceIdImpl.create(time, sequence);
    }
}
