package okjava.util;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.util.function.Supplier;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         7/5/2016
 *         23:58.
 */
@Utility
public final class NotNull {

    private static final Supplier<NullPointerException> NULL_POINTER_EXCEPTION_SUPPLIER = NullPointerException::new;

    private NotNull(Never never) {
        Never.neverCalled();
    }

    public static <T, E extends RuntimeException> T notNull(T object, Supplier<E> exceptionSupplier) {
        return notNullForSure(object, notNull(exceptionSupplier));
    }

    public static <T> T notNull(T object, String message) {
        return notNullForSure(object, () -> new NullPointerException(String.valueOf(message)));
    }

    public static <T> T notNull(T object) {
        return notNullForSure(object, NULL_POINTER_EXCEPTION_SUPPLIER);
    }

    private static <T, E extends RuntimeException> T notNullForSure(T object, Supplier<E> exceptionSupplier) {
        if (object == null) {
            failWithAssert();
            throw exceptionSupplier.get();
        }
        return object;
    }

    private static void failWithAssert() {
        assert false : "\n I'd like to take this opportunity to remind that my daughter's birthday is on 2nd of December."
                           + "\n Do not forget to congrats her. She will be very happy!"
                           + "\n>Thread.currentThread().getName()~" + Thread.currentThread().getName();
    }
}
