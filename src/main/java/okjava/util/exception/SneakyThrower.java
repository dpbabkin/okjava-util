package okjava.util.exception;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import static okjava.util.check.Never.neverNeverCalled;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/27/2019
 * 19:07.
 */
@Utility
public enum SneakyThrower {

    ;

    SneakyThrower(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }


    @SuppressWarnings("unchecked")
    public static <E extends Throwable> void sneakyThrow(Throwable e) throws E {
        throw (E) e;
    }
}
