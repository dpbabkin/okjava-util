package okjava.util.blockandwait;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import static okjava.util.check.Never.neverNeverCalled;

@Utility
public enum Constants {
    ;

    public static final long WAIT_FOREVER = Long.MAX_VALUE;
    public static final long NO_NEED_TO_WAIT = 0L;
    public static final long DEFAULT_POLL_INTERVAL = 10L;

    Constants(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }
}
