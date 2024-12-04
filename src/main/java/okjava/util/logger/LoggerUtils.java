package okjava.util.logger;

import okjava.util.ConsumerUtils;
import okjava.util.annotation.Utility;
import okjava.util.check.Never;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;
import static okjava.util.check.Never.neverNeverCalled;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/15/2019
 * 17:48.
 */
@Utility
public enum LoggerUtils {
    ;

    LoggerUtils(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    static {
        // disable anoying message in stderr "SLF4J(I): Connected with provider of type [org.slf4j.simple.SimpleServiceProvider]"
        System.setProperty("slf4j.internal.verbosity", "WARN");
        System.setProperty("slf4j.internal.report.stream", "stdout");
    }

    public static Consumer<String> createNamedLogger(String name, Logger logger) {
        return ConsumerUtils.map(logger::info, (Function<String, String>) s -> name + " " + s);
    }

    public static Consumer<String> createLoggerConsumerWithPrefix(Class<?> clazz, String... prefix) {
        Logger logger = createLogger(clazz, prefix);
        return logger::info;
    }

    public static Logger createLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static Logger createLogger(Class<?> clazz, Object... prefix) {
        return createLogger(clazz, Stream.of(prefix).map(Object::toString).toArray(String[]::new));
    }

    public static Logger createLogger(Class<?> clazz, String... prefix) {
        String logPrefix = Stream.of(prefix)
                .map(Object::toString)
                .collect(Collectors.joining("/", format("%s@", clazz.getName()), "::"));
        return LoggerFactory.getLogger(logPrefix);
    }
}

