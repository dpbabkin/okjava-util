package okjava.util;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import static okjava.util.check.Never.neverNeverCalled;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 3/30/2018
 * 21:50.
 */
@Utility
public enum OkMath {
    ;

    OkMath(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static int average(int a, int b) {
        return (a + b) >>> 1;
    }

    public static long average(long a, long b) {
        return (a + b) >>> 1;
    }
}