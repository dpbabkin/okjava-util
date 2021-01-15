package okjava.util.datetime;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 23:36.
 */
public final class DateTimeFormat {
    private static final String DEFAULT_FORMAT = "yyyy.MM.dd HH:mm:ss.SSS X";
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

    public ZonedDateTime stringToZonedDateTime(String dateTime) {
        return ZonedDateTime.parse(dateTime, formatter);
    }

    public Instant stringToInstant(String dateTime) {
        return stringToZonedDateTime(dateTime).toInstant();
    }

    public long stringToLong(String dateTime) {
        return stringToInstant(dateTime).toEpochMilli();
    }

    public String longTimeToString(long time) {
        ZonedDateTime zonedDateTime = Instant.ofEpochMilli(time).atZone(ZoneOffset.UTC);
        return zonedDateTime.format(formatter);
    }
}
