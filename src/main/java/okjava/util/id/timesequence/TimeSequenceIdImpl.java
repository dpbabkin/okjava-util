package okjava.util.id.timesequence;

import okjava.util.check.MathCheck;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 20:25.
 */
final class TimeSequenceIdImpl extends TimeSequenceIdBase {

    private final long time;
    private final long sequence;

    private TimeSequenceIdImpl(long time, long sequence) {
        this.time = MathCheck.positive(time);
        this.sequence = MathCheck.nonNegative(sequence);
    }

    static TimeSequenceIdImpl create(long time, long sequence) {
        return new TimeSequenceIdImpl(time, sequence);
    }

    public long getTime() {
        return time;
    }

    public long getSequence() {
        return sequence;
    }
}
