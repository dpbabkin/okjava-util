package okjava.util.id;

import static okjava.util.check.Never.neverNeverCalled;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/7/2019
 * 23:12.
 */
@Utility
public enum TimeSequenceIdGeneratorFactory {
    ;

    TimeSequenceIdGeneratorFactory(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    private static final IdGenerator<Long> INSTANCE = LongTimeTimeSequenceIdGenerator.i();

    public static IdGenerator<Long> i() {
        return INSTANCE;
    }

    public static IdGenerator<Long> TimeSequenceIdGenerator() {
        return INSTANCE;
    }

}
