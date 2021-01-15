package okjava.util.cache;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.function.IntFunction;

import static okjava.util.NotNull.notNull;
import static okjava.util.check.MathCheck.nonNegative;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 1/7/2017
 * 21:53.
 */
public final class IntToObjectCacheLazy<O> implements IntFunction<O> {
    private final int offset;
    private final List<O> list = Lists.newCopyOnWriteArrayList();
    private final IntFunction<O> factory;

    @SuppressWarnings("unchecked")
    private IntToObjectCacheLazy(IntFunction<O> factory, int offset) {
        this.factory = notNull(factory);
        this.offset = nonNegative(offset);
    }

    public static <O> IntFunction<O> create(IntFunction<O> factory) {
        return new IntToObjectCacheLazy<>(factory, 0);
    }

    public static <O> IntFunction<O> create(IntFunction<O> factory, int offset) {
        return new IntToObjectCacheLazy<>(factory, offset);
    }

    @Override
    public O apply(int value) {
        int index = value - offset;
        while (list.size() < index + 1) {
            list.add(null);
        }
        O object = list.get(index);
        if (object == null) {
            synchronized (list) {
                object = list.get(index);
                if (object != null) {
                    return object;
                }
                object = factory.apply(value);
                list.set(index, object);
            }
        }
        return object;
    }
}
