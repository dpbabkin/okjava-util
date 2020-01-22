package okjava.util.id.timesequence;

import okjava.util.id.LongTimeSequenceId;

import javax.annotation.Nonnull;

import static okjava.util.id.LongTimeSequenceIdUtils.fetchSequence;
import static okjava.util.id.LongTimeSequenceIdUtils.fetchTime;
import static okjava.util.id.LongTimeSequenceIdUtils.joinTimeAndSequence;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/7/2019
 * 19:53.
 */
final class LongTimeSequenceIdImpl extends TimeSequenceIdBase implements LongTimeSequenceId {

    // 2 bits reserved.
    // 42 bits time (till 2109.05.15)
    // 20 bit sequence
    private final long timeAndSequence;

    private LongTimeSequenceIdImpl(long time, long sequence) {
        this.timeAndSequence = joinTimeAndSequence(time, sequence);
    }

    static LongTimeSequenceId fromLong(long raw) {
        return create(fetchTime(raw), fetchSequence(raw));
    }

    static LongTimeSequenceId create(long time, long sequence) {
        return new LongTimeSequenceIdImpl(time, sequence);
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
        if (other instanceof LongTimeSequenceIdImpl) {
            return Long.compare(this.timeAndSequence, ((LongTimeSequenceIdImpl) other).timeAndSequence);
        }
        return super.compareTo(other);
    }

    @Override
    public int hashCode() {
        return (int) (timeAndSequence ^ (timeAndSequence >>> 32));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof LongTimeSequenceIdImpl) {
            LongTimeSequenceIdImpl that = (LongTimeSequenceIdImpl) o;
            if (this.timeAndSequence == that.timeAndSequence) {
                return true;
            }
        }
        if (!(o instanceof TimeSequenceId)) return false;
        TimeSequenceId that = (TimeSequenceId) o;
        return this.getTime() == that.getTime() && this.getSequence() == that.getSequence();
    }

    @Override
    public Long getId() {
        return timeAndSequence;
    }
}
