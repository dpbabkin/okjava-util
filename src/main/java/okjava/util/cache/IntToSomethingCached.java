package okjava.util.cache;

import java.util.function.BinaryOperator;
import java.util.function.IntFunction;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 1/11/2017
 * 19:06.
 */
public final class IntToSomethingCached<O> extends IntToSomething<O> {

    private IntFunction<O> cache;


    private IntToSomethingCached(O zero, O one, BinaryOperator<O> combiner, int min) {
        super(zero, one, combiner);
        this.cache = IntToObjectCacheLazy.create(this::applySupper, min);
    }

    public static <O> IntToSomethingCached<O> create(O zero, O one, BinaryOperator<O> combiner) {
        return create(zero, one, combiner, 0);
    }

    public static <O> IntToSomethingCached<O> create(O zero, O one, BinaryOperator<O> combiner, int min) {
        return new IntToSomethingCached<>(zero, one, combiner, min);
    }

    private O applySupper(int count) {
        return super.apply(count);
    }

    @Override
    public O apply(int count) {
        return cache.apply(count);
    }
}
