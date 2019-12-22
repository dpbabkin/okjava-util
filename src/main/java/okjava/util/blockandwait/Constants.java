package okjava.util.blockandwait;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import static okjava.util.check.Never.neverNeverCalled;

@Utility
public enum Constants {
    ;

    Constants(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static final long WAIT_FOREVER = Long.MAX_VALUE;
    public static final long NO_NEED_TO_WAIT = 0L;
}
