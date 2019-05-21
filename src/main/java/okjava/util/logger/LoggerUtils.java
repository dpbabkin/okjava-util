package okjava.util.logger;

import static okjava.util.check.Never.neverNeverCalled;

import okjava.util.ConsumerUtils;
import okjava.util.annotation.Utility;
import okjava.util.check.Never;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

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
        return ConsumerUtils.map(logger::info, s -> name + " " + s);
    }

    public static Consumer<String> createLoggerConsumerWithPrefix(Class<?> clazz, String prefix) {
        Logger logger = createLoggerWithPrefix(clazz, prefix);
        return logger::info;
    }

    public static Logger createLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static Logger createLoggerWithPrefix(Class<?> clazz, String prefix) {
        return LoggerFactory.getLogger(clazz.getName() + "._." + prefix);
    }
}

