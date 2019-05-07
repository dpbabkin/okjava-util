package okjava.util.id.timesequence;

import static okjava.util.check.Never.neverNeverCalled;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;
import okjava.util.datetime.DateTimeFormat;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 23:10.
 */
@Utility
enum IdGeneratorDateTimeFormat {
    ;

    IdGeneratorDateTimeFormat(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    private final static DateTimeFormat FORMATTER = DateTimeFormat.create("yyyyMMdd:HHmmss.SSS");

    public static String longToString(long time) {
        return FORMATTER.longToString(time);
    }

    public static String format(long time,long sequence) {
        return longToString(time) + "_" + sequence;
    }
}
