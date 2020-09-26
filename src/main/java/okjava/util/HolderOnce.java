package okjava.util;

import okjava.util.string.ToStringBuffer;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 26/9/2020
 * 20:45.
 */
public final class HolderOnce<T> implements Supplier<T>, Consumer<T> {

    private final AtomicReference<T> t = new AtomicReference<>();

    private HolderOnce(T t) {
        this.t.set(t);
    }

    private HolderOnce() {
        this(null);
    }

    public static <T> HolderOnce<T> of(T t) {
        return new HolderOnce<>(notNull(t));
    }

    public static <T> HolderOnce<T> empty() {
        return new HolderOnce<>();
    }

    @Override
    public T get() {
        T t = this.t.get();
        if (t == null) {
            ToStringBuffer.string("Invalid State. value had not been set.")
                    .throwException(IllegalStateException::new);
        }
        return t;
    }

    private T getNative() {
        return this.t.get();
    }

    @Override
    public int hashCode() {
        T t = getNative();
        return t != null ? t.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HolderOnce<?> holder = (HolderOnce<?>) o;
        T t = getNative();
        Object other = holder.getNative();
        if (t == null && other == null) {
            return true;
        }
        if (other == null) {
            return false;
        }
        return other.equals(t);
    }

    @Override
    public String toString() {
        T t = getNative();
        return this.getClass().getSimpleName() + "|" + (t != null ? t.toString() : "null");
    }

    @Override
    public void accept(T t) {
        if (!this.t.compareAndSet(null, t)) {
            ToStringBuffer.string("Invalid State. Value had already been initialized.")
                    .add("value", this.t.get())
                    .throwException(IllegalStateException::new);
        }
    }
}
