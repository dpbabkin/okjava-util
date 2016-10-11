package okjava.util;

import static java.util.Objects.requireNonNull;

import java.util.function.Supplier;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         7/2/2016
 *         20:45.
 */
public final class Final<T> implements Supplier<T> {

    private final T t;

    private Final(T t) {
        this.t = requireNonNull(t);
    }


    public static <T> Final<T> create(T t) {
        return new Final<>(t);
    }

    public static <T> Final<T> of(T t) {
        return create(t);
    }

    @Override
    public T get() {
        return t;
    }

    @Override
    public int hashCode() {
        return t.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Final<?> aFinal = (Final<?>) o;

        return t.equals(aFinal.t);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "|" + t.toString();
    }
}
