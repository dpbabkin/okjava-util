package okjava.util;

import static okjava.util.FunnyFailMessage.getFunnyFailMessage;
import static okjava.util.check.Never.neverNeverCalled;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 7/5/2016
 * 23:58.
 */
@Utility
public enum CheckEquals {
    ;
    private static final Function<String, IllegalStateException> ILLEGAL_STATE_EXCEPTION_FUNCTION = IllegalStateException::new;
    private static final Function<String, IllegalArgumentException> ILLEGAL_ARGUMENT_EXCEPTION_FUNCTION = IllegalArgumentException::new;
    private static final Function<String, IllegalArgumentException> ILLEGAL_ARGUMENT_EXCEPTION_FUNCTION_WITH_ASSERT = message -> {
        assert false : message + getFunnyFailMessage();
        return new IllegalArgumentException(message);
    };
    private static final BiFunction<Object, Object, String> STRING_FORMATTER = (object1, object2) -> object1 + " =! " + object2;


    CheckEquals(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static <O1, O2, E extends Exception> boolean checkEquals(O1 object1, O2 object2, BiFunction<O1, O2, E> exceptionSupplier) throws E {
        if (!object1.equals(object2)) {
            throw exceptionSupplier.apply(object1, object2);
        }
        return true;
    }

    public static <O1, O2> boolean checkEquals(O1 object1, O2 object2) {
        return checkEquals(object1, object2, (o1, o2) -> ILLEGAL_STATE_EXCEPTION_FUNCTION.apply(STRING_FORMATTER.apply(o1, o2)));
    }

    public static <O1, O2> boolean equalsIllegalState(O1 object1, O2 object2) {
        return checkEquals(object1, object2);
    }

    public static <O1, O2> boolean equalsIllegalArgument(O1 object1, O2 object2) {
        return checkEquals(object1, object2, (o1, o2) -> ILLEGAL_ARGUMENT_EXCEPTION_FUNCTION.apply(STRING_FORMATTER.apply(o1, o2)));
    }

    public static <O1, O2> boolean assertEquals(O1 object1, O2 object2) {
        return checkEquals(object1, object2, (o1, o2) -> ILLEGAL_ARGUMENT_EXCEPTION_FUNCTION_WITH_ASSERT.apply(STRING_FORMATTER.apply(o1, o2)));
    }
}
