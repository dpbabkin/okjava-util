package okjava.util.has;

import okjava.util.id.timesequence.TimeSequenceId;
import okjava.util.string.ToStringBuffer;

import static okjava.util.id.TimeSequenceIdGeneratorFactory.timeSequenceIdGenerator;
import static okjava.util.id.timesequence.TimeSequenceIdFactory.timeSequenceIdFactory;

public abstract class HasTimeSequenceIdImpl extends HasIdImpl<TimeSequenceId> implements HasTimeSequenceId {

    public HasTimeSequenceIdImpl(long id) {
        this(timeSequenceIdFactory().fromLong(id));
    }

    public HasTimeSequenceIdImpl(TimeSequenceId timeSequenceId) {
        super(timeSequenceId);
    }

    public HasTimeSequenceIdImpl() {
        this(timeSequenceIdGenerator().generate());
    }

    @Override
    public String toString() {
        return ToStringBuffer.of(this).
                timeSequenceId(this)
                .toString();
    }
}
