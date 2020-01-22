package okjava.util.datetime;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static okjava.util.check.Never.neverNeverCalled;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 23:36.
 */
@Utility
public enum DateTimeFormatUtils {

    ;

    DateTimeFormatUtils(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static LocalDateTime convertMillisToLocalDateTime(long time) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneOffset.UTC);
    }
}
