package okjava.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         7/2/2016
 *         20:45.
 */
public final class Holder<T> implements Supplier<T>, Consumer<T> {

    private T t;

    private Holder(T t) {
        this.t = t;
    }

    public static <T> Holder<T> create(T t) {
        return new Holder<>(t);
    }

    public static <T> Holder<T> of(T t) {
        return create(t);
    }

    @Override
    public T get() {
        return t;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Holder<?> holder = (Holder<?>) o;

        return t != null ? t.equals(holder.t) : holder.t == null;
    }

    @Override
    public int hashCode() {
        return t != null ? t.hashCode() : 0;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "|" + (t != null ? t.toString() : "null");
    }

    @Override
    public void accept(T t) {
        this.t = t;
    }
}
