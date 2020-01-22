package okjava.util.id.format;

import okjava.util.annotation.Singleton;
import okjava.util.id.LongTimeSequenceId;
import okjava.util.id.timesequence.TimeSequenceId;
import okjava.util.id.timesequence.TimeSequenceIdFactory;
import okjava.util.string.ToStringBuffer;

import java.time.format.DateTimeParseException;
import java.util.function.Function;

import static okjava.util.check.Once.calledOnce;
import static okjava.util.id.LongTimeSequenceIdUtils.ifUnderLimit;
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

    private final Function<String, Long> parser = this::parse;
    private final Function<String, LongTimeSequenceId> timeSequenceIdParser = this::parseToLongTimeSequenceId;

    private interface Handler<R> {
        R handle(long time, long sequence);
    }

    private final Handler<TimeSequenceId> timeSequenceIdHandler = new TimeSequenceIdHandler();
    private final Handler<LongTimeSequenceId> longTimeSequenceIdHandler = new LongTimeSequenceIdHandler();
    private final Handler<Long> longHandler = new LongHandler();

    private <R> R parseToTimeSequenceId(String id, Handler<R> handler) {
        String[] split = id.split(TimeSequenceIdFormatConstants.SEPARATOR);
        if (split.length != 2) {
            throw new IllegalArgumentException(id);
        }
        try {
            long time = TimeSequenceIdFormatConstants.FORMATTER.stringToLong(split[0]);
            long sequence = Long.parseLong(split[1]);
            return handler.handle(time, sequence);
        } catch (NumberFormatException | IndexOutOfBoundsException | DateTimeParseException e) {
            throw new IllegalArgumentException(id, e);
        }
    }

    public TimeSequenceId parseToTimeSequenceId(String id) {
        return parseToTimeSequenceId(id, timeSequenceIdHandler);
    }

    public LongTimeSequenceId parseToLongTimeSequenceId(String id) {
        return parseToTimeSequenceId(id, longTimeSequenceIdHandler);
    }

    public Long parse(String id) {
        return parseToTimeSequenceId(id, longHandler);
    }

    public Function<String, Long> getParser() {
        return parser;
    }

    public Function<String, LongTimeSequenceId> getTimeSequenceIdParser() {
        return timeSequenceIdParser;
    }

    private final static class TimeSequenceIdHandler implements Handler<TimeSequenceId> {

        @Override
        public TimeSequenceId handle(long time, long sequence) {
            return TimeSequenceIdFactory.timeSequenceIdFactory().create(time, sequence);
        }
    }

    private final static class LongTimeSequenceIdHandler implements Handler<LongTimeSequenceId> {

        @Override
        public LongTimeSequenceId handle(long time, long sequence) {
            return TimeSequenceIdFactory.timeSequenceIdFactory().createLongTimeSequence(time, sequence);
        }
    }

    private final static class LongHandler implements Handler<Long> {

        @Override
        public Long handle(long time, long sequence) {
            if (ifUnderLimit(time, sequence)) {
                return joinTimeAndSequence(time, sequence);
            }
            throw ToStringBuffer.string("createLongTimeSequence").add("time", time).add("sequence", sequence).toException(IllegalArgumentException::new);
        }
    }
}
