package okjava.util.id.format;

import okjava.util.annotation.Singleton;
import okjava.util.id.timesequence.TimeSequenceId;
import okjava.util.id.timesequence.TimeSequenceIdFactory;

import java.time.format.DateTimeParseException;
import java.util.function.Function;

import static okjava.util.check.Once.calledOnce;
import static okjava.util.id.LongTimeSequenceIdUtils.joinTimeAndSequence;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 23:10.
 */
@Singleton
public final class TimeSequenceIdParser {
    private static final TimeSequenceIdParser INSTANCE = new TimeSequenceIdParser();

    private TimeSequenceIdParser() {
        calledOnce(this.getClass());
    }

    public static TimeSequenceIdParser i() {
        return INSTANCE;
    }

    public static TimeSequenceIdParser create() {
        return INSTANCE;
    }

    public static TimeSequenceIdParser timeSequenceIdParser() {
        return INSTANCE;
    }


    private final Function<String, Long > parser = this::parse;
    private final Function<String,TimeSequenceId > timeSequenceIdParser = this::parseToTimeSequenceId;


    public TimeSequenceId parseToTimeSequenceId(String id) {
        return TimeSequenceIdFactory.timeSequenceIdFactory().fromLong(parse(id));
    }

    public long parse(String id) {
        String[] split = id.split(TimeSequenceIdFormatConstants.SEPARATOR);
        if (split.length != 2) {
            throw new IllegalArgumentException(id);
        }
        try {
            long time = TimeSequenceIdFormatConstants.FORMATTER.stringToLong(split[0]);
            long sequence = Long.parseLong(split[1]);
            return joinTimeAndSequence(time, sequence);
        } catch (NumberFormatException | IndexOutOfBoundsException | DateTimeParseException e) {
            throw new IllegalArgumentException(id, e);
        }
    }

    public Function<String, Long> getParser() {
        return parser;
    }

    public Function<String, TimeSequenceId> getTimeSequenceIdParser() {
        return timeSequenceIdParser;
    }
}
