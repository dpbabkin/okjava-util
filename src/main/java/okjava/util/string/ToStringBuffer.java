package okjava.util.string;

import okjava.util.has.HasTimeSequenceId;
import okjava.util.id.timesequence.LongTimeSequenceIdUtils;

import static okjava.util.NotNull.notNull;
import static okjava.util.string.ToStringUtils.i2s;
import static okjava.util.string.ToStringUtils.m2s;
import static okjava.util.string.ToStringUtils.nullable;

import java.util.Collection;
import java.util.Map;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 1/20/2019
 * 19:10.
 */
public class ToStringBuffer {

    private final StringBuilder builder = new StringBuilder();


    private ToStringBuffer(String name) {
        this.builder.append(notNull(name));
        this.builder.append(" { ");
    }

    public static ToStringBuffer create(String name) {
        return new ToStringBuffer(name);
    }

    public static <C> ToStringBuffer ofClass(Class<C> clazz) {
        return create(clazz.getSimpleName());
    }

    public static <O> ToStringBuffer of(O object) {
        return ofClass(object.getClass());
    }

    public <O> ToStringBuffer id(long id) {
        return add("id", id);
    }

    public <O> ToStringBuffer id(HasTimeSequenceId id) {
        return add("id", id.getStringId());
    }

    public <O> ToStringBuffer add(String name, O value) {
        this.builder.append(name).append("=").append(nullable(value)).append(" ");
        return this;
    }

    public <O> ToStringBuffer nl() {
        this.builder.append(System.lineSeparator());
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
}
