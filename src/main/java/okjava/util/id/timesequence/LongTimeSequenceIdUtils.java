package okjava.util.id.timesequence;

import static okjava.util.check.Never.neverNeverCalled;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/7/2019
 * 23:05.
 */

@Utility
public enum LongTimeSequenceIdUtils {
    ;
    LongTimeSequenceIdUtils(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    private static final long MAX_SEQUENCE = (1L << 20) - 1;// 1048575 = bx11111111111111111111 (20)
    private static final long MAX_TIME = (1L << 42) - 1;// 4398046511103 = bx111111111111111111111111111111111111111111 (42) 2109.05.15 07:35:11.103
    private static final long RESERVED_BITS = (3L << 62);// two highest bits
    //private static final long SEQUENCE_MASK =


    public static String formatId(long raw) {
        return IdGeneratorDateTimeFormat.format(fetchTime(raw), fetchSequence(raw));
    }

    static boolean ifUnderLimit(long time, long sequence) {
        return time <= MAX_TIME && sequence <= MAX_SEQUENCE;
    }

    public static long joinTimeAndSequence(long time, long sequence) {

        assert time <= MAX_TIME : time;
        assert sequence <= MAX_SEQUENCE : sequence;
        //MathCheck.lessThenOrEqual(time, MAX_TIME);
        //MathCheck.lessThenOrEqual(sequence, MAX_SEQUENCE);

        long timeAndSequence = time << 20;
        timeAndSequence += sequence;


        assert timeAndSequence > 0;
        assert (timeAndSequence & RESERVED_BITS) == 0; //two highest bits are reserved.
        return timeAndSequence;
    }

    public static long fetchTime(long raw) {
        assert (raw >>> 20)>0:raw;
        return raw >>> 20;
    }

    public static long fetchSequence(long raw) {
        return raw & MAX_SEQUENCE;
    }

    public static long incrementSequence(long raw) {
        assert fetchSequence(raw) < MAX_SEQUENCE;
        return raw + 1;
    }

}