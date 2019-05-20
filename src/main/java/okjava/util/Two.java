package okjava.util;

import static okjava.util.NotNull.notNull;

import okjava.util.function.BiSupplier;
import okjava.util.string.ToStringBuffer;

import java.util.Objects;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 10/25/2015
 * 15:28.
 */
public final class Two<A, B> implements BiSupplier<A, B> {

    private final A a;
    private final B b;

    private Two(A a, B b) {
        this.a = notNull(a);
        this.b = notNull(b);
    }

    public static <A, B> Two<A, B> two(A a, B b) {
        return create(a, b);
    }

    public static <A, B> Two<A, B> create(A a, B b) {
        return new Two<>(a, b);
    }

    @Override
    public A getA() {
        return a;
    }

    @Override
    public B getB() {
        return b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Two<?, ?> two = (Two<?, ?>) o;
        return Objects.equals(a, two.a)
                   && Objects.equals(b, two.b);
    }

    @Override
    public String toString() {
        return ToStringBuffer.of(this)
                   .add("a", a)
                   .add("b", b)
                   .toString();
    }
}
