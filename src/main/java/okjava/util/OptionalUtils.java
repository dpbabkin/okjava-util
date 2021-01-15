package okjava.util;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;
import okjava.util.e.EEFunction;
import okjava.util.e.EFunction;

import java.util.Optional;
import java.util.stream.Stream;

import static okjava.util.NotNull.notNull;
import static okjava.util.StreamUtils.toStream;
import static okjava.util.check.Never.neverNeverCalled;


/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/4/2016
 * 23:43.
 */
@Utility
public enum OptionalUtils {
    ;

    OptionalUtils(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static <T> Optional<T> or(Optional<T> first, Optional<T> second) {
        return first.isPresent() ? first : second;
    }

    public static <T> Optional<T> or(Optional<T> first, Optional<T> second, Optional<T> third) {
        return or(or(first, second), third);
    }

    /**
     * This method is equivalent to
     * <pre> {@code
     * for (Optional<T> optional : optionals) {
     *     if (optional.isPresent()) {
     *         return optional;
     *     }
     * }
     * return Optional.empty();
     * }</pre>
     *
     * @param optionals Iterable of {@link java.util.Optional}
     * @param <T>       Type of the Optional value
     * @return first Optional in the iterable having value.
     */
    public static <T> Optional<T> or(Iterable<Optional<T>> optionals) {
        return or(toStream(optionals));
    }


    public static <T> Optional<T> or(Stream<Optional<T>> optionals) {
        return optionals.flatMap(OptionalUtils::stream).findFirst();
    }


    /**
     * Will be deprecated after release of this feature in 1.9
     *
     * <p>https://bugs.openjdk.java.net/browse/JDK-8050820
     * http://hg.openjdk.java.net/jdk9/jdk9/jdk/rev/ed38ff66f7dd
     *
     * @param optional {@code Optional} to convert
     * @param <T>      Type of the non-existent value
     * @return the optional value as a {@code Stream}
     */
    public static <T> Stream<T> stream(Optional<T> optional) {
        return optional.map(Stream::of).orElse(Stream.of());
    }


    public static <T, R, E extends Exception> Optional<R> mapE(Optional<T> optional, EFunction<T, R, E> function) throws E {
        notNull(function);
        if (!optional.isPresent()) {
            return Optional.empty();
        }
        return Optional.ofNullable(function.apply(optional.get()));
    }

    public static <T, R, E1 extends Exception, E2 extends Exception> Optional<R> mapEE(Optional<T> optional, EEFunction<T, R, E1, E2> function) throws E1, E2 {
        notNull(function);
        if (!optional.isPresent()) {
            return Optional.empty();
        }
        return Optional.ofNullable(function.apply(optional.get()));
    }


    public static <T, R, E extends Exception> Optional<R> flatMap(Optional<T> optional, EFunction<T, Optional<R>, E> function) throws E {
        notNull(function);
        if (!optional.isPresent()) {
            return Optional.empty();
        }
        return notNull(function.apply(optional.get()));
    }
}
