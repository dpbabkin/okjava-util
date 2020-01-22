package okjava.util.has;

import okjava.util.id.LongTimeSequenceId;
import okjava.util.id.TimeSequenceIdGeneratorFactory;
import okjava.util.id.timesequence.TimeSequenceIdFactory;
import okjava.util.string.ToStringBuffer;

public class HasLongTimeSequenceIdImpl extends HasIdImpl<LongTimeSequenceId> implements HasLongTimeSequenceId {

    public HasLongTimeSequenceIdImpl(long id) {
        super(TimeSequenceIdFactory.timeSequenceIdFactory().fromLong(id));
    }

    public HasLongTimeSequenceIdImpl(LongTimeSequenceId timeSequenceId) {
        super(timeSequenceId);
    }

    public HasLongTimeSequenceIdImpl() {
        this(TimeSequenceIdGeneratorFactory.timeSequenceIdGenerator().generate());
    }

    @Override
    public String toString() {
        return ToStringBuffer.of(this).
                timeSequenceId(this)
                .toString();
    }
}
