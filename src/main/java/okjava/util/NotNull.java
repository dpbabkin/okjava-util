package okjava.util;

import static okjava.util.FunnyFailMessage.getFunnyFailMessage;
import static okjava.util.FunnyFailMessage.getFunnyFailMessageSupplier;
import static okjava.util.check.Never.neverNeverCalled;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;
import okjava.util.string.ToStringBuffer;

import java.util.function.Supplier;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 7/5/2016
 * 23:58.
 */
@Utility
public enum NotNull {
    ;

    private static final Supplier<NullPointerException> NULL_POINTER_EXCEPTION_SUPPLIER = NullPointerException::new;
    private static final Supplier<NullPointerException> NULL_POINTER_EXCEPTION_SUPPLIER_WITH_ASSERT_ERROR = () -> {
        failWithAssert();
        return NULL_POINTER_EXCEPTION_SUPPLIER.get();
    };

    NotNull(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static <T, E extends Exception> T notNull(T object, Supplier<E> exceptionSupplier) throws E {
        return notNullForSure(object, notNull(exceptionSupplier));
    }

    public static <T> T notNull(T object, String message) {
        return notNullForSure(object, () -> new NullPointerException(String.valueOf(message)));
    }

    public static <T> T assertNotNull(T object) {
        return assertNotNull(object, getFunnyFailMessageSupplier());
    }

    public static <T> T assertNotNull(T object, String message) {
        assert object != null : message;
        return object;
    }

    public static <T> T assertNotNull(T object, Supplier<String> messageSupplier) {
        assert object != null : messageSupplier.get();
        return object;
    }

    public static <T> T notNull(T object) {
        return notNullForSure(object, NULL_POINTER_EXCEPTION_SUPPLIER);
    }

    private static <T, E extends Exception> T notNullForSure(T object, Supplier<E> exceptionSupplier) throws E {
        if (object == null) {
            throw exceptionSupplier.get();
        }
        return object;
    }

    public static <T> void checkEquals(T object1,T object2) {
        if (!object1.equals(object2)) {
            throw ToStringBuffer.string("checkEquals")
                    .add("object1",object1)
                    .add("object2",object2)
                    .toException(IllegalStateException::new);
        }
    }

    private static void failWithAssert() {
        assert false : getFunnyFailMessage();
    }
}
