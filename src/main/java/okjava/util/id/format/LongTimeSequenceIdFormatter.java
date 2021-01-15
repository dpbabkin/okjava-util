package okjava.util.id.format;

import okjava.util.annotation.Singleton;

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
final class LongTimeSequenceIdFormatter implements Function<Long, String> {
    private static final LongTimeSequenceIdFormatter INSTANCE = new LongTimeSequenceIdFormatter();

    private LongTimeSequenceIdFormatter() {
        calledOnce(this.getClass());
    }

    static LongTimeSequenceIdFormatter longTimeSequenceIdFormatter() {
        return INSTANCE;
    }

    private static String longTimeToString(long time) {
        return TimeSequenceIdFormatConstants.FORMATTER.longTimeToString(time);
    }

    String format(long time, long sequence) {
        return longTimeToString(time) + TimeSequenceIdFormatConstants.SEPARATOR + sequence;
    }

    @Override
    public String apply(Long id) {
        return format(fetchTime(id), fetchSequence(id));
    }
}
