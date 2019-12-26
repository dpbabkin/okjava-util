package okjava.util;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.util.concurrent.TimeUnit;

import static okjava.util.check.Never.neverNeverCalled;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/17/2016
 * 16:31.
 */
@Utility
public enum WaitTestUtils {
    ;

    WaitTestUtils(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static final long DEFAULT_WAIT_TIME = 1_000L;
    public static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.MILLISECONDS;
}
