package okjava.util.collections;

import static okjava.util.check.Never.neverNeverCalled;

import com.google.common.collect.ImmutableList;
import okjava.util.annotation.Utility;
import okjava.util.check.DummyException;
import okjava.util.check.Never;
import okjava.util.e.EFunction;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 7/7/2016
 * 19:31.
 */
@Utility
public enum ConvertUtils {
    ;

    ConvertUtils(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static <I, O, C extends Collection<O>, E extends Exception> C transform(Iterable<? extends I> input, C output, Function<I, Optional<O>> itemResolver,
                                                                                   Function<I, E> exceptionProvider) throws E {
        for (I item : input) {
            Optional<O> optional = itemResolver.apply(item);
            output.add(optional.orElseThrow(() -> exceptionProvider.apply(item)));
        }
        return output;
    }

    public static <I, O, C extends Collection<O>, E extends Exception> C transform(Iterable<? extends I> input, C output, EFunction<I, O, E> itemResolver) throws E {
        for (I item : input) {
            O o = itemResolver.apply(item);
            output.add(o);
        }
        return output;
    }

    public static <I, O, C extends Collection<O>> Optional<C> transform(Iterable<? extends I> input, C output, Function<I, Optional<O>> itemResolver) {
        try {
            return Optional.of(ConvertUtils.transform(input, output, itemResolver, i -> DummyException.create()));
        } catch (DummyException e) {
            return Optional.empty();
        }
    }

    public static <A, B> List<B> transform(List<A> input, Function<? super A, ? extends B> mapper) {
        return input.stream().map(mapper).collect(ImmutableList.toImmutableList());
    }
}
