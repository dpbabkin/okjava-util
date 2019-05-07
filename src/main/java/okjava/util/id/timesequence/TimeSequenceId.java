package okjava.util.id.timesequence;

import static okjava.util.id.timesequence.TimeSequenceIdComparator.timeSequenceIdComparator;

import javax.annotation.Nonnull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 20:25.
 */
public interface TimeSequenceId extends Comparable<TimeSequenceId> {
    long getTime();

    long getSequence();


    @Override
    default int compareTo(@Nonnull TimeSequenceId other) {
        return timeSequenceIdComparator().compare(this, other);
    }
}
