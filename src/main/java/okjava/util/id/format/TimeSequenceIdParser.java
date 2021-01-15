package okjava.util.id.format;

import okjava.util.annotation.Singleton;
import okjava.util.id.timesequence.TimeSequenceId;
import okjava.util.id.timesequence.TimeSequenceIdFactory;

import java.time.format.DateTimeParseException;
import java.util.function.Function;

import static okjava.util.check.Once.calledOnce;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 23:10.
 */
@Singleton
final class TimeSequenceIdParser implements Function<String, TimeSequenceId> {
    private static final TimeSequenceIdParser INSTANCE = new TimeSequenceIdParser();

    private TimeSequenceIdParser() {
        calledOnce(this.getClass());
    }

    static TimeSequenceIdParser timeSequenceIdParser() {
        return INSTANCE;
    }

    @Override
    public TimeSequenceId apply(String id) {
        String[] split = id.split(TimeSequenceIdFormatConstants.SEPARATOR);
        if (split.length != 2) {
            throw new IllegalArgumentException(id);
        }
        try {
            long time = TimeSequenceIdFormatConstants.FORMATTER.stringToLong(split[0]);
            long sequence = Long.parseLong(split[1]);
            return TimeSequenceIdFactory.timeSequenceIdFactory().create(time, sequence);
        } catch (NumberFormatException | IndexOutOfBoundsException | DateTimeParseException e) {
            throw new IllegalArgumentException(id, e);
        }
    }
}
