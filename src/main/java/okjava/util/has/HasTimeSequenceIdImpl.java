package okjava.util.has;

import okjava.util.id.LongTimeSequenceId;
import okjava.util.id.TimeSequenceIdGeneratorFactory;
import okjava.util.id.timesequence.TimeSequenceId;
import okjava.util.id.timesequence.TimeSequenceIdFactory;
import okjava.util.string.ToStringBuffer;

public abstract class HasTimeSequenceIdImpl extends HasIdImpl<TimeSequenceId> implements HasTimeSequenceId {

    public HasTimeSequenceIdImpl(long id) {
        super(TimeSequenceIdFactory.timeSequenceIdFactory().fromLong(id));
    }

    public HasTimeSequenceIdImpl(TimeSequenceId timeSequenceId) {
        super(timeSequenceId);
    }

    public HasTimeSequenceIdImpl() {
        this(TimeSequenceIdGeneratorFactory.timeSequenceIdGenerator().generate());
    }

    @Override
    public String toString() {
        return ToStringBuffer.of(this).
                timeSequenceId(this)
                .toString();
    }
}
