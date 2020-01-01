package okjava.util.datetime;

import static okjava.util.id.timesequence.LongTimeSequenceIdUtils.convertMillisToLocalDateTime;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 23:36.
 */
final public class DateTimeFormat {

    private static final String DEFAULT_FORMAT = "yyyy.MM.dd HH:mm:ss.SSS";
    private static final DateTimeFormat DEFAULT_INSTANCE = DateTimeFormat.create(DEFAULT_FORMAT);

    private final DateTimeFormatter formatter;

    private DateTimeFormat(String format) {
        this.formatter = DateTimeFormatter.ofPattern(format);
    }

    public static DateTimeFormat create() {
        return DEFAULT_INSTANCE;
    }

    public static DateTimeFormat create(String format) {
        return new DateTimeFormat(format);
    }

    public long stringToLong(String dateTime) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
        return localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    public String longTimeToString(long time) {
        LocalDateTime dateTime = convertMillisToLocalDateTime(time);
        return dateTime.format(formatter);
    }
}
