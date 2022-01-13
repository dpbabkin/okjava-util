package okjava.util.string;

import com.google.common.collect.ImmutableList;
import okjava.util.datetime.DateTimeFormat;
import okjava.util.has.HasTimeSequenceId;
import okjava.util.id.timesequence.TimeSequenceId;
import org.slf4j.Logger;
import org.slf4j.spi.LoggingEventBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static okjava.util.NotNull.notNull;
import static okjava.util.id.timesequence.TimeSequenceIdFactory.timeSequenceIdFactory;
import static okjava.util.string.ToStringUtils.i2s;
import static okjava.util.string.ToStringUtils.m2s;
import static okjava.util.string.ToStringUtils.nullable;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 1/20/2019
 * 19:10.
 */
public class ToStringBuffer {

    private static final String SEPARATOR = " ";
    private static final String EQUAL = "=";
    private final static List<Class<? extends Throwable>> exceptionTypes = ImmutableList.of(RuntimeException.class, java.lang.Error.class, Exception.class,
            Throwable.class);
    private final StringBuilder builder = new StringBuilder();
    private Throwable throwable = null;
    private String toString = null;

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

    //public <O> ToStringBuffer addTimeSequence(String name, long id) {
    //    return  timeSequenceId(TimeSequenceIdFactory.timeSequenceIdFactory().fromLong(id));
    //add(name, TimeSequenceIdFormatter.timeSequenceIdFormatter().format(id));
    //}

    private static String getType(Throwable throwable) {
        return exceptionTypes.stream()
                .filter(c -> c.isAssignableFrom(throwable.getClass()))
                .findFirst()
                .orElseThrow(() -> new java.lang.Error("Throwable, must be assignable at least from Throwable."))
                .getSimpleName();
    }

    public ToStringBuffer timeSequenceId(long id) {
        return timeSequenceId(timeSequenceIdFactory().fromLong(id));
    }

    public ToStringBuffer timeSequenceId(HasTimeSequenceId id) {
        return timeSequenceId(id.getId());
    }

    public ToStringBuffer timeSequenceId(TimeSequenceId id) {
        return add("id", id.toString());
    }

    public ToStringBuffer ln() {
        return addRaw(System.lineSeparator());
    }

    public ToStringBuffer addRaw(String line) {
        this.builder.append(line);
        return this;
    }

    public ToStringBuffer addNullableThrowable(Throwable throwable) {
        if (throwable != null) {
            return addThrowable(throwable);
        }
        return this;
    }

    public ToStringBuffer addThrowable(Throwable throwable) {
        if (this.throwable != null) {
            ToStringBuffer.string("throwable already set.").addThrowable(this.throwable).toException(IllegalStateException::new);
        }
        this.throwable = throwable;
        String type = getType(throwable);
        return add(type, ToStringBuffer
                .string(throwable.getClass().getName())
                .addNullable("message", throwable.getMessage())
                .addThread()
                .toString());
    }

    public ToStringBuffer addException(Exception exception) {
        return addThrowable(exception);
    }

    public ToStringBuffer addThread() {
        return addThread(Thread.currentThread());
    }

    public ToStringBuffer addThread(Thread t) {
        return add("thread", t.getName());
    }

    public ToStringBuffer addTime() {
        return add("time", DateTimeFormat.create().longTimeToString(System.currentTimeMillis()));
    }

    public <O> ToStringBuffer addNullable(String name, O value) {
        if (value != null) {
            return add(name, value);
        }
        return this;
    }

    public <O> ToStringBuffer add(O object) {
        if (object instanceof java.lang.String) {
            ToStringBuffer.string("Probably not what you want")
                    .name("object").addWithClass().value(object)
                    //.addWithClass("object", object)
                    .throwException(IllegalArgumentException::new);
        }
        return add(object.getClass().getSimpleName(), object);
    }

    public <O> ToStringBuffer addWithClass(String name, O value) {
        return this.name(name).addWithClass().value(value);
    }

    public Name name(String name) {
        return new Name(name);
    }

    public <O> ToStringBuffer add(String name, O value) {
        this.builder.append(name).append(EQUAL).append(nullable(value)).append(SEPARATOR);
        return this;
    }

    public <T> ToStringBuffer add(String name, Collection<T> collection) {
        return add(name, i2s(collection));
    }

    public <K, V> ToStringBuffer add(String name, Map<K, V> map) {
        return add(name, m2s(map));
    }

    @Override
    public String toString() {
        if (toString == null) {
            this.builder.append("}");
            toString = builder.toString();
        }
        return toString;
    }

    public ToStringBuffer assertStatement(boolean statement) {
        assert statement : this.toString();
        return this;
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
        if (logger.isDebugEnabled()) {
            toLogger(logger.atDebug());
        }
    }

    public void toTrace(Logger logger) {
        if (logger.isTraceEnabled()) {
            toLogger(logger.atTrace());
        }
    }

    private void toLogger(LoggingEventBuilder loggingEventBuilder) {
        loggingEventBuilder.log(this.toString());
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

    public void toConsumer(Consumer<String> consumer) {
        consumer.accept(this.toString());
    }

    public RuntimeException toIllegalArgumentException() {
        return toException(IllegalArgumentException::new);
    }

    public RuntimeException toIllegalStateException() {
        return toException(IllegalStateException::new);
    }

    public <E extends Exception> E toException(Function<String, E> function) {
        return function.apply(toString());
    }

    public <E extends Exception> void throwException(Function<String, E> function) throws E {
        throw toException(function);
    }

    public class Name {
        private final String name;
        private final Decorator<String> nameDecorator;
        private final Decorator<Object> valueDecorator;

        Name(
                String name,
                Decorator<String> nameDecorator, Decorator<Object> valueDecorator) {
            this.name = notNull(name);
            this.nameDecorator = notNull(nameDecorator);
            this.valueDecorator = notNull(valueDecorator);
        }


        private Name(String name) {
            this(name, Decorator.create(), Decorator.create());
        }

        private static String nullableClass(Object object) {
            return object == null ? "" : object.getClass().getSimpleName();
        }

        public Name wrapQuotes() {
            return new Name(this.name, this.nameDecorator, this.valueDecorator.decorateBefore(s -> "'" + s + "'"));
        }

        public Name addWithClass() {
            return new Name(this.name, this.nameDecorator, this.valueDecorator.decorateAfter(s -> "[" + nullableClass(s) + "]=" + s));
        }

        public <O> ToStringBuffer value(O value) {
            return add(this.nameDecorator.decorate(name), this.valueDecorator.decorate(value));
        }
    }
}
