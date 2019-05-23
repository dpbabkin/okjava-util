package okjava.util.datetime;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 23:36.
 */
public class DateTimeFormat {

    private static final String DEFAULT_FORMAT = "yyyy.MM.dd HH:mm:ss.SSS";
    private static final DateTimeFormat DEFAULT_INSTANCE = DateTimeFormat.create(DEFAULT_FORMAT);
    private final DateTimeFormatter formatter;
    //DateTimeFormatter.ofPattern("yyyyMMdd HHmmssSSS");

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

    public String longToString(long time) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneOffset.UTC);
        return dateTime.format(formatter);
    }
}