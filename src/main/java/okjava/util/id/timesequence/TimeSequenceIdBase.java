package okjava.util.id.timesequence;


import okjava.util.id.format.TimeSequenceIdForPer;

import javax.annotation.Nonnull;

import static okjava.util.id.format.TimeSequenceIdForPer.timeSequenceIdForPer;
import static okjava.util.id.timesequence.TimeSequenceIdComparator.timeSequenceIdComparator;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 20:25.
 */
abstract class TimeSequenceIdBase implements TimeSequenceId {

    @Override
    public int compareTo(@Nonnull TimeSequenceId other) {
        return timeSequenceIdComparator().compare(this, other);
    }

    @Override
    public int hashCode() {
        return 31 * Long.hashCode(getTime()) + Long.hashCode(getSequence());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeSequenceId)) return false;
        TimeSequenceId that = (TimeSequenceId) o;
        return this.getTime() == that.getTime() && this.getSequence() == that.getSequence();
    }

    @Override
    public String toString() {
        // format to yyyyMMdd.HHmmss.SSSX_sequence
        return timeSequenceIdForPer().format(this);
    }
}
