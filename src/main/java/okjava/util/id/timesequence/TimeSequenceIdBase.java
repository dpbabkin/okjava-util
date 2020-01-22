package okjava.util.id.timesequence;


import okjava.util.id.format.TimeSequenceIdFormatter;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 20:25.
 */
abstract class TimeSequenceIdBase implements TimeSequenceId {

    @Override
    public int hashCode() {
        long raw = this.getTime() << 20;
        raw += this.getSequence();
        return (int) (raw ^ (raw >>> 32));
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
        // format to yyyyMMdd.HHmmss.SSS_sequence
        return TimeSequenceIdFormatter.timeSequenceIdFormatter().format(getTime(), getSequence());
    }
}
