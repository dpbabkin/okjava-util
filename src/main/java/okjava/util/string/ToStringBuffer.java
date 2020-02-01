package okjava.util.string;

import com.google.common.collect.ImmutableList;
import okjava.util.datetime.DateTimeFormat;
import okjava.util.has.HasLongTimeSequenceId;
import okjava.util.id.format.TimeSequenceIdFormatter;
import org.slf4j.Logger;
import org.slf4j.spi.LoggingEventBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static okjava.util.NotNull.notNull;
import static okjava.util.string.ToStringUtils.i2s;
import static okjava.util.string.ToStringUtils.m2s;
import static okjava.util.string.ToStringUtils.nullable;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 1/20/2019
 * 19:10.
 */
public class ToStringBuffer {

    private final StringBuilder builder = new StringBuilder();
    private static final String SEPARATOR = " ";

    private ToStringBuffer(String name) {
        this.builder.append(notNull(name));
        this.builder.append(" { ");
    }

    public static ToStringBuffer string(String name) {
        return new ToStringBuffer(name);
    }

    public static <C> ToStringBuffer ofClass(Class<C> clazz) {
        return string(clazz.getSimpleName());
    }

    public static <O> ToStringBuffer of(O object) {
        return ofClass(object.getClass());
    }

    public <O> ToStringBuffer timeSequenceId(long id) {
        return addTimeSequence("id", id);
    }

    public <O> ToStringBuffer timeSequenceId(HasLongTimeSequenceId id) {
        return add("id", id.getStringId());
    }

    public <O> ToStringBuffer ln() {
        return addRaw(System.lineSeparator());
    }

    public <O> ToStringBuffer addRaw(String line) {
        this.builder.append(line);
        return this;
    }

    public <O> ToStringBuffer addTimeSequence(String name, long id) {
        return add(name, TimeSequenceIdFormatter.timeSequenceIdFormatter().format(id));
    }

    private Throwable throwable = null;

    public <O> ToStringBuffer addThrowable(Throwable throwable) {
        if (this.throwable != null) {
            ToStringBuffer.string("throwable already set.").addThrowable(this.throwable).toException(IllegalStateException::new);
        }
        this.throwable = throwable;
        String type = getType(throwable);
        return add(type, ToStringBuffer.string(throwable.getMessage()).add("class", throwable.getClass().getName()));
        //return add(type + ".getClass()", throwable.getClass().getName()).
        //        add(type + ".getMessage()", throwable.getMessage());
    }

    private final static List<Class<? extends Throwable>> exceptionTypes = ImmutableList.of(RuntimeException.class, java.lang.Error.class, Exception.class, Throwable.class);

    private static String getType(Throwable throwable) {
        return exceptionTypes.stream()
                .filter(c -> c.isAssignableFrom(throwable.getClass()))
                .findFirst()
                .orElseThrow(() -> new java.lang.Error("Throwable, must be assignable at least from Throwable."))
                .getSimpleName();
    }

    public <O> ToStringBuffer addException(Exception exception) {
        return add("exception.getMessage()", exception.getMessage());
    }

    public <O> ToStringBuffer addThread() {
        return addThread(Thread.currentThread());
    }

    public <O> ToStringBuffer addThread(Thread t) {
        return add("thread", t.getName());
    }

    public <O> ToStringBuffer addTime() {
        return add("time", DateTimeFormat.create().longTimeToString(System.currentTimeMillis()));
    }

    public <O> ToStringBuffer addNotNull(String name, O value) {
        if (value != null) {
            return add(name, value);
        }
        return this;
    }

    public <O> ToStringBuffer add(String name, O value) {
        this.builder.append(name).append("=").append(nullable(value)).append(SEPARATOR);
        return this;
    }

    public <T> ToStringBuffer add(String name, Collection<T> collection) {
        return add(name, i2s(collection));
    }

    public <K, V> ToStringBuffer add(String name, Map<K, V> map) {
        return add(name, m2s(map));
    }

    private String toString = null;

    @Override
    public String toString() {
        if (toString == null) {
            this.builder.append("}");
            toString = builder.toString();
        }
        return toString;
    }

    public ToStringBuffer assertFalse() {
        assert false : this.toString();
        return this;
    }

    public ToStringBuffer toError(Logger logger, Throwable throwable) {
        return addThrowable(throwable).toError(logger);
    }

    public ToStringBuffer toError(Logger logger) {
        if (this.throwable != null) {
            return toLogger(logger.atError(), throwable);
        }
        return toLogger(logger.atError(), throwable);
    }

    public void toWarning(Logger logger) {
        toLogger(logger.atWarn());
    }

    public void toInfo(Logger logger) {
        toLogger(logger.atInfo());
    }

    public void toDebug(Logger logger) {
        toLogger(logger.atDebug());
    }

    public void toTrace(Logger logger) {
        toLogger(logger.atTrace());
    }

    private ToStringBuffer toLogger(LoggingEventBuilder loggingEventBuilder) {
        loggingEventBuilder.log(this.toString());
        return this;
    }

    private ToStringBuffer toLogger(LoggingEventBuilder loggingEventBuilder, Throwable throwable) {
        loggingEventBuilder.log(this.toString(), throwable);
        return this;
    }

    public Supplier<String> toSupplier() {
        return new Supplier<>() {
            @Override
            public String get() {
                return this.toString();
            }

            @Override
            public String toString() {
                return ToStringBuffer.this.toString();
            }
        };
    }

    public <E extends Exception> E toException(Function<String, E> function) {
        return function.apply(toString());
    }
}
