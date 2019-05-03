package okjava.util.string;

import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 1/20/2019
 * 19:10.
 */
public class ToStringBuffer {

    private final StringBuilder builder = new StringBuilder();


    public static ToStringBuffer object(String name) {
        return new ToStringBuffer(name);
    }

    public static ToStringBuffer of(Object object) {
        return object(object.getClass().getSimpleName());
    }


    private ToStringBuffer(String name) {
        this.builder.append(notNull(name));
        this.builder.append("{ ");
    }

    public ToStringBuffer add(String name, Object value) {
        this.builder.append(name).append("=").append(value).append(" ");
        return this;
    }

    @Override
    public String toString() {
        this.builder.append("}");
        return builder.toString();
    }
}
