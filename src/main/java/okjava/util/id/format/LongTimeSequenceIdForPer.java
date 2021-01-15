package okjava.util.id.format;

import okjava.util.forper.ABForPer;
import okjava.util.forper.ForPer;
import okjava.util.id.LongTimeSequenceId;
import okjava.util.id.timesequence.TimeSequenceId;
import okjava.util.string.ToStringBuffer;

import static okjava.util.id.format.LongTimeSequenceIdFormatter.longTimeSequenceIdFormatter;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 15.01.2021 19:37
 */
@Deprecated // never used.
public final class LongTimeSequenceIdForPer extends ABForPer<Long> {

    private final static ForPer<Long> INSTANCE = new LongTimeSequenceIdForPer();

    public static ForPer<Long> longTimeSequenceIdForPer() {
        return INSTANCE;
    }

    private LongTimeSequenceIdForPer() {
        super(LongTimeSequenceIdForPer::parseToLong, longTimeSequenceIdFormatter());
    }

    private static Long parseToLong(String id) {
        TimeSequenceId timeSequenceId = TimeSequenceIdParser.timeSequenceIdParser().apply(id);
        if (timeSequenceId instanceof LongTimeSequenceId) {
            return ((LongTimeSequenceId) timeSequenceId).getRawLong();
        }
        throw ToStringBuffer.string("createLongTimeSequence").add("id", id).toException(IllegalArgumentException::new);
    }
}
