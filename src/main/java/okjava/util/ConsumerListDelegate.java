package okjava.util;

import com.google.common.collect.ImmutableList;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/7/2018
 * 21:34.
 */
public class ConsumerListDelegate<T> implements Consumer<T> {

    private final Collection<? extends Consumer<T>> delegates;

    private ConsumerListDelegate(Collection<? extends Consumer<T>> delegates) {
        this.delegates = ImmutableList.copyOf(delegates);
    }

    public static <T> Consumer<T> create(Collection<? extends Consumer<T>> delegates) {
        return new ConsumerListDelegate<>(delegates);
    }

    @Override
    public void accept(T t) {
        delegates.forEach(cc -> cc.accept(t));
    }
}
