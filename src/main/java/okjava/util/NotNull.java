package okjava.util;

import static okjava.util.check.Never.neverNeverCalled;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.util.function.Supplier;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         7/5/2016
 *         23:58.
 */
@Utility
public enum NotNull {
    ;

    NotNull(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }


    private static final Supplier<NullPointerException> NULL_POINTER_EXCEPTION_SUPPLIER = NullPointerException::new;
    private static final Supplier<NullPointerException> NULL_POINTER_EXCEPTION_SUPPLIER_WITH_ASSERT_ERROR = () -> {
        failWithAssert();
        return NULL_POINTER_EXCEPTION_SUPPLIER.get();
    };


    public static <T, E extends Exception> T notNull(T object, Supplier<E> exceptionSupplier) throws E {
        return notNullForSure(object, notNull(exceptionSupplier));
    }


    public static <T> T notNull(T object, String message) {
        return notNullForSure(object, () -> new NullPointerException(String.valueOf(message)));
    }


    public static <T> T notNull(T object) {
        return notNullForSure(object, NULL_POINTER_EXCEPTION_SUPPLIER_WITH_ASSERT_ERROR);
    }


    private static <T, E extends Exception> T notNullForSure(T object, Supplier<E> exceptionSupplier) throws E {
        if (object == null) {
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
