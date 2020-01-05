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

    public static Consumer<String> createNamedLogger(String name, Logger logger) {
        return ConsumerUtils.map(logger::info, (Function<String, String>) s -> name + " " + s);
    }

    public static Consumer<String> createLoggerConsumerWithPrefix(Class<?> clazz, String ... prefix) {
        Logger logger = createLoggerWithPrefix(clazz, prefix);
        return logger::info;
    }

    public static Logger createLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static Logger createLoggerWithPrefix(Class<?> clazz, Object... prefix) {
        String logPrefix = Stream.of(prefix)
                .map(Object::toString)
                .collect(Collectors.joining("//", "__", "$"));

        return LoggerFactory.getLogger(clazz.getName() + logPrefix);
    }
}

