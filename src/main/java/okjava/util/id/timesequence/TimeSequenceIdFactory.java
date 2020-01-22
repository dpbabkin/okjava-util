package okjava.util.id.timesequence;

import okjava.util.annotation.Singleton;
import okjava.util.id.LongTimeSequenceId;
import okjava.util.string.ToStringBuffer;

import static okjava.util.check.Once.calledOnce;
import static okjava.util.id.LongTimeSequenceIdUtils.ifUnderLimit;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/7/2019
 * 23:12.
 */
@Singleton
public final class TimeSequenceIdFactory {

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

    public LongTimeSequenceId fromLong(long id) {
        return LongTimeSequenceIdImpl.fromLong(id);
    }

    public LongTimeSequenceId createLongTimeSequence(long time, long sequence) {
        if (ifUnderLimit(time, sequence)) {
            return LongTimeSequenceIdImpl.create(time, sequence);
        }
        throw ToStringBuffer.string("createLongTimeSequence").add("time", time).add("sequence", sequence).toException(IllegalArgumentException::new);
    }

    public TimeSequenceId create(long time, long sequence) {
        if (ifUnderLimit(time, sequence)) {
            return LongTimeSequenceIdImpl.create(time, sequence);
        }
        return TimeSequenceIdImpl.create(time, sequence);
    }
}
