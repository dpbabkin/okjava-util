package okjava.util.ifelse;

import static okjava.util.NotNull.notNull;
import static okjava.util.check.Never.neverNeverCalled;
import static okjava.util.empty.EmptyConsumer.emptyConsumer;
import static okjava.util.empty.EmptyRunnable.emptyRunnable;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 7/3/2016
 * 21:51.
 */
@Utility
public enum IfElse {
    ;

    IfElse(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static void ifElse(BooleanSupplier condition, Runnable ifRunnable, Runnable elseRunnable) {
        if (condition.getAsBoolean()) {
            notNull(elseRunnable);
            ifRunnable.run();
        } else {
            notNull(ifRunnable);
            elseRunnable.run();
        }
    }

    public static void ifElse(Supplier<Boolean> condition, Runnable ifRunnable, Runnable elseRunnable) {
        ifElse((BooleanSupplier) condition::get, ifRunnable, elseRunnable);
    }

    public static <T> void ifElse(Supplier<T> supplier, Predicate<T> predicate, Consumer<T> ifConsumer, Consumer<T> elseConsumer) {
        ifElse(supplier.get(), predicate, ifConsumer, elseConsumer);
    }

    public static <T> void ifElse(T object, Predicate<T> predicate, Consumer<T> ifConsumer, Consumer<T> elseConsumer) {
        ifElse(() -> predicate.test(object), () -> ifConsumer.accept(object), () -> elseConsumer.accept(object));
    }

    public static <T> void ifNull(Supplier<T> supplier, Runnable elseRunnable) {
        ifNull(supplier.get(), elseRunnable);
    }

    public static <T> void ifNull(T object, Runnable elseRunnable) {
        ifNotNullElse(object, emptyConsumer(), elseRunnable);
    }

    public static <T> void ifNotNull(Supplier<T> supplier, Consumer<T> ifConsumer) {
        ifNotNull(supplier.get(), ifConsumer);
    }

    public static <T> void ifNotNull(T object, Consumer<T> ifConsumer) {
        ifNotNullElse(object, ifConsumer, emptyRunnable());
    }

    public static <T> void ifNotNullElse(Supplier<T> supplier, Consumer<T> ifConsumer, Runnable elseRunnable) {
        ifNotNullElse(supplier.get(), ifConsumer, elseRunnable);
    }

    public static <T> void ifNotNullElse(T object, Consumer<T> ifConsumer, Runnable elseRunnable) {
        ifElse(object, Objects::nonNull, ifConsumer, o -> elseRunnable.run());
    }
}
