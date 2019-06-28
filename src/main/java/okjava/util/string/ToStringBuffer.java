package okjava.util.string;

import okjava.util.has.HasTimeSequenceId;
import okjava.util.id.timesequence.LongTimeSequenceIdUtils;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

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

    public <O> ToStringBuffer timeSequenceId(HasTimeSequenceId id) {
        return addTimeSequence("id", id.getId());
    }

    public <O> ToStringBuffer ln() {
        this.builder.append(System.lineSeparator());
        return this;
    }

    public <O> ToStringBuffer line(String line) {
        this.builder.append(line).append(SEPARATOR);
        return this;
    }

    public <O> ToStringBuffer addTimeSequence(String name, long id) {
        return add(name, LongTimeSequenceIdUtils.format(id));
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

    public <E extends Exception> E toException(Function<String, E> function) {
        return function.apply(toString());
    }
}
