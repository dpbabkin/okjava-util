package okjava.util.id.timesequence;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;
import okjava.util.datetime.DateTimeFormat;

import java.time.format.DateTimeParseException;

import static okjava.util.check.Never.neverNeverCalled;
import static okjava.util.id.timesequence.LongTimeSequenceIdUtils.joinTimeAndSequence;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 23:10.
 */
@Utility
enum TimeSequenceIdFormat {
    ;

    private final static DateTimeFormat FORMATTER = DateTimeFormat.create("yyyyMMdd:HHmmss.SSS");

    TimeSequenceIdFormat(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    static String longTimeToString(long time) {
        return FORMATTER.longTimeToString(time);
    }

    static String format(long time, long sequence) {
        return longTimeToString(time) + "_" + sequence;
    }

    public static long parse(String id) {
        String[] split = id.split("_");
        //assert split.length == 2 : id;
        if (split.length != 2) {
            throw new IllegalArgumentException(id);
        }
        try {
            long time = FORMATTER.stringToLong(split[0]);
            long sequence = Long.parseLong(split[1]);
            return joinTimeAndSequence(time, sequence);
        } catch (NumberFormatException | IndexOutOfBoundsException | DateTimeParseException e) {
            throw new IllegalArgumentException(id, e);
        }
    }
}