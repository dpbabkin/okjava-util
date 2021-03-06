package okjava.util.check;

import okjava.util.annotation.Utility;

/**
 * The instance of this class should never been created.
 *
 * <p>It is the same as {@link java.lang.Void}. But I decided to create my own one and put into it some useful functions.
 *
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 9/17/2016
 * 14:44.
 * @see java.lang.Void
 */
@Utility
public final class Never {

    private Never(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static void neverNeverCalled() {
        neverNeverCalled("should never been called");
    }

    public static void neverNeverCalled(String message) {
        fail(message);
    }

    static void fail(String message) {
        if (message == null) {
            message = "null";
        }
        assert false : message;
        throw new TheBiggestInstantiationErrorEverEverEver(message);
    }

    private static final class TheBiggestInstantiationErrorEverEverEver extends Error {
        TheBiggestInstantiationErrorEverEverEver(String message) {
            super(message);
        }
    }
}
