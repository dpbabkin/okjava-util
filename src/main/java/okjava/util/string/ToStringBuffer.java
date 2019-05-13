package okjava.util.string;

import static okjava.util.NotNull.notNull;
import static okjava.util.string.ToStringUtils.nullable;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 1/20/2019
 * 19:10.
 */
public class ToStringBuffer {

    private final StringBuilder builder = new StringBuilder();


    public static ToStringBuffer create(String name) {
        return new ToStringBuffer(name);
    }

    public static ToStringBuffer ofClass(Class<?> clazz) {
        return create(clazz.getSimpleName());
    }

    public static ToStringBuffer of(Object object) {
        return ofClass(object.getClass());
    }

    private ToStringBuffer(String name) {
        this.builder.append(notNull(name));
        this.builder.append("{ ");
    }

    public ToStringBuffer add(String name, Object value) {
        this.builder.append(name).append("=").append(nullable(value)).append(" ");
        return this;
    }

    @Override
    public String toString() {
        this.builder.append("}");
        return builder.toString();
    }
}
