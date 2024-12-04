package okjava.util;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.util.function.Function;

import static java.util.function.Function.identity;
import static okjava.util.check.Never.neverNeverCalled;


/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/1/2016
 * 22:52.
 */
@Utility
public enum Clazz {
    ;

    Clazz(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static <T> T cast(Object object) {
        return cast(object, identity());
    }


    public static <T> T cast(Object object, Class<T> clazz) {
        if (clazz.isAssignableFrom(object.getClass())) {
            return cast(object);
        }
        throw new ClassCastException("class: " + object.getClass() + " can not be assigned to class: " + clazz);
    }


    public static <T, E extends Exception> T cast(Object object, Class<T> clazz, Function<ClassCastException, E> exceptionSupplier) throws E {
        if (clazz.isAssignableFrom(object.getClass())) {
            return cast(object);
        }
        throw exceptionSupplier.apply(new ClassCastException("class: " + object.getClass() + " can not be assigned to class: " + clazz));
    }


    /**
     * Helper function to simplifier cast.
     * <p>
     * <p/>Using this function, this code:
     * <pre> {@code
     * try {
     *     MyType myType = (MyType) object;
     * } catch (ClassCastException e) {
     *     throw new MyWrongTypeException("wrong class. expected: " + MyType.class + " actual: " + object.getClass() e);
     * }
     * }</pre>
     * <p>
     * <p/>can be replaced by this code:
     * <pre> {@code
     * MyType myType = Clazz.cast(object, e -> new MyWrongTypeException("wrong class. expected: " + MyType.class + " actual: " + object.getClass(), e));
     * }</pre>
     */
    @SuppressWarnings("unchecked") // exception handled
    public static <T, E extends Exception> T cast(Object object, Function<ClassCastException, E> exceptionSupplier) throws E {
        try {
            return (T) object;
        } catch (ClassCastException e) {
            throw exceptionSupplier.apply(e);
        }
    }
}
