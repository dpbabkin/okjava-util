package okjava.util.id.format;

import okjava.util.annotation.Singleton;
import okjava.util.id.timesequence.TimeSequenceId;

import java.util.function.Function;

import static okjava.util.check.Once.calledOnce;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 23:10.
 */
@Singleton
final class TimeSequenceIdFormatter implements Function<TimeSequenceId, String> {
    private static final TimeSequenceIdFormatter INSTANCE = new TimeSequenceIdFormatter();

    private TimeSequenceIdFormatter() {
        calledOnce(this.getClass());
    }

    static TimeSequenceIdFormatter timeSequenceIdFormatter() {
        return INSTANCE;
    }

    @Override
    public String apply(TimeSequenceId timeSequenceId) {
        return format(timeSequenceId.getTime(), timeSequenceId.getSequence());
    }

    private static String longTimeToString(long time) {
        return TimeSequenceIdFormatConstants.FORMATTER.longTimeToString(time);
    }

    private String format(long time, long sequence) {
        return longTimeToString(time) + TimeSequenceIdFormatConstants.SEPARATOR + sequence;
    }
}
