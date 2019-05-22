package okjava.util.id.timesequence;

import static okjava.util.id.timesequence.LongTimeSequenceIdUtils.fetchSequence;
import static okjava.util.id.timesequence.LongTimeSequenceIdUtils.fetchTime;
import static okjava.util.id.timesequence.LongTimeSequenceIdUtils.joinTimeAndSequence;

import javax.annotation.Nonnull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/7/2019
 * 19:53.
 */
final class LongTimeSequenceId extends TimeSequenceIdBase implements TimeSequenceId {

    // 2 bits reserved.
    // 42 bits time (till 2109.05.15)
    // 20 bit sequence
    private final long timeAndSequence;

    private LongTimeSequenceId(long time, long sequence) {
        this.timeAndSequence = joinTimeAndSequence(time, sequence);
    }

    public static TimeSequenceId fromLong(long raw) {
        return create(fetchTime(raw), fetchSequence(raw));
    }

    public static TimeSequenceId create(long time, long sequence) {
        return new LongTimeSequenceId(time, sequence);
    }

    @Override
    public long getTime() {
        return fetchTime(this.timeAndSequence);
    }

    @Override
    public long getSequence() {
        return fetchSequence(this.timeAndSequence);
    }

    @Override
    public int compareTo(@Nonnull TimeSequenceId other) {
        if (other instanceof LongTimeSequenceId) {
            return Long.compare(this.timeAndSequence, ((LongTimeSequenceId) other).timeAndSequence);
        }
        return super.compareTo(other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof LongTimeSequenceId) {
            LongTimeSequenceId that = (LongTimeSequenceId) o;
            if (this.timeAndSequence == that.timeAndSequence) {
                return true;
            }
        }
        if (!(o instanceof TimeSequenceId)) return false;
        TimeSequenceId that = (TimeSequenceId) o;
        return this.getTime() == that.getTime() && this.getSequence() == that.getSequence();
    }

    @Override
    public int hashCode() {
        return (int) (timeAndSequence ^ (timeAndSequence >>> 32));
    }
}
