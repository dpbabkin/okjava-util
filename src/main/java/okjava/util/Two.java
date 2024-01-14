package okjava.util;

import okjava.util.function.BiSupplier;
import okjava.util.string.ToStringBuffer;

import java.util.Objects;
import java.util.function.Function;

import static okjava.util.NotNull.notNull;

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

    public <A1> Two<A1, B> setA(A1 a1) {
        return create(a1, getB());
    }

    public <A1> Two<A1, B> mapA(Function<A,A1> mapper) {
        return create(mapper.apply(getA()), getB());
    }

    @Override
    public B getB() {
        return b;
    }

    public <B1> Two<A, B1> setB(B1 b1) {
        return create(getA(), b1);
    }

    public <B1> Two<A, B1> mapB(Function<B,B1> mapper) {
        return create(getA(), mapper.apply(getB()));
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
