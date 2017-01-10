package okjava.util.cache;

import static okjava.util.check.MathCheck.lessThenOrEqual;
import static okjava.util.check.MathCheck.nonNegative;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.function.IntFunction;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         1/7/2017
 *         21:53.
 */
public class IntToObject<O> implements IntFunction<O> {
    private final int min;
    private final List<O> list;

    @SuppressWarnings("unchecked")
    public IntToObject(IntFunction<O> factory, int min, int max) {
        lessThenOrEqual(min, max);
        this.min = nonNegative(min);

        Object[] array = new Object[max - min + 1];
        for (int i = min; i <= max; i++) {
            array[i - min] = factory.apply(i);
        }

        this.list = ImmutableList.copyOf((O[]) array);
    }

    @Override
    public O apply(int value) {
        return list.get(value - min);
    }
}
