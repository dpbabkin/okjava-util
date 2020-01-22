package okjava.util.id.format;

import okjava.util.annotation.Singleton;
import okjava.util.id.LongTimeSequenceId;
import okjava.util.id.timesequence.TimeSequenceId;

import java.util.function.Function;

import static okjava.util.check.Once.calledOnce;
import static okjava.util.id.LongTimeSequenceIdUtils.fetchSequence;
import static okjava.util.id.LongTimeSequenceIdUtils.fetchTime;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 23:10.
 */
@Singleton
public final class TimeSequenceIdFormatter {
    private static final TimeSequenceIdFormatter INSTANCE = new TimeSequenceIdFormatter();

    private TimeSequenceIdFormatter() {
        calledOnce(this.getClass());
    }

    public static TimeSequenceIdFormatter i() {
        return INSTANCE;
    }

    public static TimeSequenceIdFormatter create() {
        return INSTANCE;
    }

    public static TimeSequenceIdFormatter timeSequenceIdFormatter() {
        return INSTANCE;
    }

    private final Function<Long, String> formatter = this::format;
    private final Function<LongTimeSequenceId, String> timeSequenceIdFormatter = this::format;

    private static String longTimeToString(long time) {
        return TimeSequenceIdFormatConstants.FORMATTER.longTimeToString(time);
    }

    public String format(long time, long sequence) {
        return longTimeToString(time) + TimeSequenceIdFormatConstants.SEPARATOR + sequence;
    }

    public String format(long id) {
        return format(fetchTime(id), fetchSequence(id));
    }

    public String format(TimeSequenceId timeSequenceId) {
        return timeSequenceId.toString();
    }

    public Function<Long, String> getFormatter(){
        return formatter;
    }

    public Function<LongTimeSequenceId, String> getTimeSequenceIdFormatter(){
        return timeSequenceIdFormatter;
    }
}
