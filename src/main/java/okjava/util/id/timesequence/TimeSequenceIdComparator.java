package okjava.util.id.timesequence;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.util.Comparator;

import static okjava.util.check.Never.neverNeverCalled;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/7/2019
 * 19:55.
 */
@Utility
enum TimeSequenceIdComparator {
    ;

    private static final Comparator<TimeSequenceId> TIME_SEQUENCE_ID_COMPARATOR
        = Comparator.comparingLong(TimeSequenceId::getTime).thenComparing(TimeSequenceId::getSequence);

    TimeSequenceIdComparator(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static Comparator<TimeSequenceId> timeSequenceIdComparator() {
        return TIME_SEQUENCE_ID_COMPARATOR;
    }
}
