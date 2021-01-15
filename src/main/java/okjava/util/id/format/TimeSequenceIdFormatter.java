package okjava.util.id.format;

import okjava.util.annotation.Singleton;
import okjava.util.id.timesequence.TimeSequenceId;

import java.util.function.Function;

import static okjava.util.check.Once.calledOnce;
import static okjava.util.id.LongTimeSequenceIdUtils.fetchSequence;
import static okjava.util.id.LongTimeSequenceIdUtils.fetchTime;
import static okjava.util.id.format.LongTimeSequenceIdFormatter.longTimeSequenceIdFormatter;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 23:10.
 */
@Singleton
final class TimeSequenceIdFormatter {
    private static final TimeSequenceIdFormatter INSTANCE = new TimeSequenceIdFormatter();

    private TimeSequenceIdFormatter() {
        calledOnce(this.getClass());
    }


    static TimeSequenceIdFormatter timeSequenceIdFormatter() {
        return INSTANCE;
    }

    private final Function<TimeSequenceId, String> timeSequenceIdFormatter = this::format;

    String format(TimeSequenceId timeSequenceId) {
        return longTimeSequenceIdFormatter().format(timeSequenceId.getTime(), timeSequenceId.getSequence());
    }

    Function<TimeSequenceId, String> getFormatter() {
        return timeSequenceIdFormatter;
    }
}
