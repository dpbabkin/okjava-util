package okjava.util.bit;

import static okjava.util.check.Never.neverNeverCalled;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/7/2019
 * 18:33.
 */
@Utility
public enum BitUtils {
    ;

    private static final long[] ARRAY_OF_ONE_BIT = new long[65];
    private static final long[] ARRAY_OF_MASK = new long[65];

    static {
        ARRAY_OF_ONE_BIT[0] = 0;
        for (int i = 1; i < 65; i++) {
            ARRAY_OF_ONE_BIT[i] = 1L << (i - 1);
            ARRAY_OF_MASK[i] = (ARRAY_OF_ONE_BIT[i] << 1) - 1L;
        }
    }

    @SuppressWarnings("unused")
    BitUtils(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static long rightMask(long number, int bitCount) {
        return ARRAY_OF_MASK[bitCount] & number;

    }

    public static int rightBitCount(long number) {
        int i = 0;
        while (number != 0) {
            number = number >>> 1;
            i++;
        }
        return i;


    }

    static int rightBitCount01(long number) {
        return binarySearchRightBitCount(number);
    }

    static int rightBitCount02(long number) {
        if (number == 0) {
            return 0;
        }
        return Long.toBinaryString(number).length();
    }

    private static int binarySearchRightBitCount(long number) {
        if (number == 0) return 0;
        number = Long.highestOneBit(number);

        int low = 0;
        int high = 64;
        int mid = -1;
        while (low <= high) {
            mid = (low + high) >>> 1;
            long midVal = number & ARRAY_OF_MASK[mid];

            if (midVal == 0)
                low = mid + 1;
            else
                high = mid - 1;
        }
        return high + 1;
    }
}
