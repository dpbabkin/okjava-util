package okjava.util.string;

import com.google.common.collect.ImmutableList;
import okjava.util.datetime.DateTimeFormat;
import okjava.util.has.HasLongTimeSequenceId;
import okjava.util.id.format.TimeSequenceIdFormatter;

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
        this.builder.append(System.lineSeparator());
        return this;
    }

    public <O> ToStringBuffer addRaw(String line) {
        this.builder.append(line);
        return this;
    }

    public <O> ToStringBuffer addTimeSequence(String name, long id) {
        return add(name, TimeSequenceIdFormatter.timeSequenceIdFormatter().format(id));
    }

    public <O> ToStringBuffer addThrowable(Throwable throwable) {
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
                .orElseThrow(() -> new java.lang.Error("fatal. Throwable, must be assignable at least from Throwable."))
                .getName();
    }

    private static String getType_OLD(Throwable throwable) {

        if (throwable instanceof RuntimeException) {
            return RuntimeException.class.getSimpleName();
        }
        if (throwable instanceof java.lang.Error) {
            return java.lang.Error.class.getSimpleName();
        }
        if (throwable instanceof Exception) {
            return Exception.class.getSimpleName();
        }
        return Throwable.class.getSimpleName();
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

    @Override
    public String toString() {
        this.builder.append("}");
        return builder.toString();
    }

    public Supplier<String> toSupplier() {
        return this::toString;
    }

    public <E extends Exception> E toException(Function<String, E> function) {
        return function.apply(toString());
    }
}
