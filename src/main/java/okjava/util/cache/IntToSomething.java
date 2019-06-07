package okjava.util.cache;

import static okjava.util.NotNull.notNull;

import java.util.function.BinaryOperator;
import java.util.function.IntFunction;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 1/11/2017
 * 19:06.
 */
public final class IntToSomething<O> implements IntFunction<O> {

    private final O zero;
    private final O one;
    private final BinaryOperator<O> combiner;

    IntToSomething(O zero, O one, BinaryOperator<O> combiner) {
        this.zero = notNull(zero);
        this.one = notNull(one);
        this.combiner = notNull(combiner);
    }

    public static <O> IntToSomething<O> create(O zero, O one, BinaryOperator<O> combiner) {
        return new IntToSomething<>(zero, one, combiner);
    }

    @Override
    public O apply(int count) {
        assert count >= 0 : "" + count;
        if (count == 0) {
            return getZero();
        }
        if ((count & 0x1) == 1) { //odd
            return combine(getOne(), apply(count - 1));
        }
        O half = apply(count >> 1);
        return combine(half, half);
    }


    private O getZero() {
        return zero;
    }

    private O getOne() {
        return one;
    }

    private O combine(O first, O second) {
        return combiner.apply(first, second);
    }
}
