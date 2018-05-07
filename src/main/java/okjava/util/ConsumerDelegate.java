package okjava.util;

import com.google.common.collect.ImmutableList;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/7/2018
 * 21:34.
 */
public class ConsumerDelegate<T> implements Consumer<T> {

    private final Collection<? extends Consumer<T>> delegates;

    public static <T> Consumer<T> create(Collection<? extends Consumer<T>> delegates) {
        return new ConsumerDelegate<>(delegates);
    }

    private ConsumerDelegate(Collection<? extends Consumer<T>> delegates) {
        this.delegates = ImmutableList.copyOf(delegates);
    }

    @Override
    public void accept(T t) {
        delegates.forEach(cc -> cc.accept(t));
    }
}
