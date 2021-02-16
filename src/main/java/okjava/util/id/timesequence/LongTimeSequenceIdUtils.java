package okjava.util.id.timesequence;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;
import okjava.util.string.ToStringBuffer;

import static okjava.util.check.Never.neverNeverCalled;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/7/2019
 * 23:05.
 */

@Utility
enum LongTimeSequenceIdUtils {
    ;

    private static final long MAX_SEQUENCE = (1L << 20) - 1;// 1_048_575 = bx11111111111111111111 (20)
    private static final long MAX_TIME = (1L << 42) - 1;// 4_398_046_511_103 = bx111111111111111111111111111111111111111111 (42) 2109.05.15 07:35:11.103
    private static final long RESERVED_BITS = (3L << 62);// two highest bits

    LongTimeSequenceIdUtils(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    static boolean isUnderLimit(long time, long sequence) {
        assert time > 0 : time;
        assert sequence >= 0 : sequence;
        return time <= MAX_TIME && sequence <= MAX_SEQUENCE;
    }

    static long joinTimeAndSequence(long time, long sequence) {

        assert time > 0 : time;
        assert time <= MAX_TIME : time;
        assert sequence >= 0 : sequence;
        assert sequence <= MAX_SEQUENCE : sequence;

        long timeAndSequence = time << 20;
        timeAndSequence += sequence;

        assert timeAndSequence > 0;
        assert (timeAndSequence & RESERVED_BITS) == 0; //two highest bits are reserved.

        return timeAndSequence;
    }

    static long fetchTime(long raw) {
        assert (raw >>> 20) > 0 : raw;
        return raw >>> 20;
    }

    static long fetchSequence(long raw) {
        return raw & MAX_SEQUENCE;
    }

    static boolean isMaxSequence(long raw) {
        final long sequence = fetchSequence(raw);

        if (sequence < MAX_SEQUENCE) {
            return false;
        } else if (sequence == MAX_SEQUENCE) {
            return true;
        }
        assert false : raw;
        throw ToStringBuffer.string("MAX_SEQUENCE value exceeded.")
                .add("raw", raw)
                .add("sequence", sequence)
                .add("MAX_SEQUENCE", MAX_SEQUENCE)
                .toException(IllegalStateException::new);
    }

    static long incrementSequence(long raw) {
        assert fetchSequence(raw) < MAX_SEQUENCE : raw;
        return raw + 1;
    }
}