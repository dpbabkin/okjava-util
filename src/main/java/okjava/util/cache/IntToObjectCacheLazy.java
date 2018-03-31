package okjava.util.cache;

import static okjava.util.NotNull.notNull;
import static okjava.util.check.MathCheck.nonNegative;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 1/7/2017
 * 21:53.
 */
public final class IntToObjectCacheLazy<O> implements IntFunction<O> {
    private final int min;
    private final List<O> list = new ArrayList<>();
    private final IntFunction<O> factory;

    public static <O> IntFunction<O> create(IntFunction<O> factory) {
        return new IntToObjectCacheLazy<>(factory, 0);
    }

    public static <O> IntFunction<O> create(IntFunction<O> factory, int min) {
        return new IntToObjectCacheLazy<>(factory, min);
    }

    @SuppressWarnings("unchecked")
    private IntToObjectCacheLazy(IntFunction<O> factory, int min) {
        this.factory = notNull(factory);
        this.min = nonNegative(min);
    }

    @Override
    public O apply(int value) {
        int index = value - min;
        while (list.size() < index + 1) {
            synchronized (list) {
                list.add(null);
            }
        }
        O object = list.get(index);
        if (object == null) {
            synchronized (list) {
                object = list.get(index);
                if(object!=null){
                    return object;
                }
                object = factory.apply(value);
                list.set(index, object);
            }
        }
        return object;
    }
}
