package okjava.util.cache;

import static okjava.util.check.MathCheck.lessThen;
import static okjava.util.check.MathCheck.nonNegative;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.function.IntFunction;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         1/7/2017
 *         21:53.
 */
public final class IntToObjectCacheImmutableFixed<O> implements IntFunction<O> {
    private final int min;
    private final List<O> list;

    public static <O> IntFunction<O> create(IntFunction<O> factory, int min, int max) {
        if (min == max) {
            return i -> {
                throw new IndexOutOfBoundsException("" + i);
            };
        }
        return new IntToObjectCacheImmutableFixed<>(factory, min, max);
    }

    @SuppressWarnings("unchecked")
    private IntToObjectCacheImmutableFixed(IntFunction<O> factory, int min, int max) {
        lessThen(min, max);
        this.min = nonNegative(min);

        O[] array = (O[]) new Object[max - min + 1];
        for (int i = min; i <= max; i++) {
            array[i - min] = factory.apply(i);
        }

        this.list = ImmutableList.copyOf(array);
    }

    @Override
    public O apply(int value) {
        return list.get(value - min);
    }
}
