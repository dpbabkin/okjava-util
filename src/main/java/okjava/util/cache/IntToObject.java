package okjava.util.cache;

import static okjava.util.check.MathCheck.lessThenOrEqual;
import static okjava.util.check.MathCheck.nonNegative;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
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

    public IntToObject(IntFunction<O> factory, int min, int max) {
        lessThenOrEqual(min, max);
        this.min = nonNegative(min);

        List<O> tmpList = new ArrayList<O>(max - min + 1);
        for (int i = min; i <= max; i++) {
            tmpList.set(i - min, factory.apply(i));
        }
        this.list = ImmutableList.copyOf(tmpList);
    }

    @Override
    public O apply(int value) {
        return list.get(value - min);
    }
}
