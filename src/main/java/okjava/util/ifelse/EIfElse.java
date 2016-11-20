package okjava.util.ifelse;

import static java.util.Objects.requireNonNull;
import static okjava.util.check.Never.neverNeverCalled;

import okjava.util.annotation.Utility;
import okjava.util.check.Dummy;
import okjava.util.check.Never;
import okjava.util.e.EConsumer;
import okjava.util.e.EPredicate;
import okjava.util.e.ERunnable;
import okjava.util.e.ESupplier;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         7/3/2016
 *         21:51.
 * @see okjava.util.ifelse.IfElse
 * @deprecated I do not like this class. Usages of this class prohibited until I like it. Better to use {@link okjava.util.ifelse.IfElse}
 */
@Deprecated
@Utility
public final class EIfElse {


    private EIfElse(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }


    public static <T, E extends Exception> T tryCatch(ESupplier<T, E> supplier, Function<Exception, T> exceptionHandler) {
        try {
            return supplier.get();
        } catch (Exception e) {
            return exceptionHandler.apply(e);
        }
    }

    public static <E extends Exception> void tryCatch(ERunnable<E> runnable, Consumer<Exception> exceptionHandler) {
        tryCatch(() -> {
            runnable.run();
            return Dummy.create();
        }, e -> {
            exceptionHandler.accept(e);
            return Dummy.create();
        });
    }

    public static <T, E extends Exception> void ifElse(T object,
                                                       EPredicate<T, E> predicate,
                                                       EConsumer<T, E> ifConsumer,
                                                       EConsumer<T, E> elseConsumer,
                                                       Consumer<Exception> exceptionHandler) {
        tryCatch(() -> {
            requireNonNull(ifConsumer);
            requireNonNull(elseConsumer);
            if (predicate.test(object)) {
                ifConsumer.accept(object);
            } else {
                elseConsumer.accept(object);
            }
        }, exceptionHandler);
        requireNonNull(ifConsumer);
        requireNonNull(elseConsumer);
    }


    public static <T, E extends Exception> void ifNull(ESupplier<T, E> supplier, ERunnable<E> elseRunnable, Consumer<Exception> exceptionHandler) {
        try {
            ifNull(supplier.get(), elseRunnable, exceptionHandler);
        } catch (Exception e) {
            exceptionHandler.accept(e);
        }
    }


    /**
     * Run runnable if object is null.
     */
    public static <T, E extends Exception> void ifNull(T object, ERunnable<E> runnable, Consumer<Exception> exceptionHandler) {
        ifElse(object, Objects::nonNull, o -> {
        }, o -> runnable.run(), exceptionHandler);
    }


    /**
     * Run consumer and pass object to it if object is not null.
     */
    public static <T, E extends Exception> void ifNotNull(T object, EConsumer<T, E> consumer, Consumer<Exception> exceptionHandler) {
        ifElse(object, Objects::nonNull, consumer, o -> {
        }, exceptionHandler);
    }


    public static <T, E extends Exception> void ifNotNullElse(T object, EConsumer<T, E> ifConsumer, ERunnable<E> elseRunnable, Consumer<Exception> exceptionHandler) {
        ifElse(object, Objects::nonNull, ifConsumer, o -> elseRunnable.run(), exceptionHandler);
    }
}