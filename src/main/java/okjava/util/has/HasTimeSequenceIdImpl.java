package okjava.util.has;

import okjava.util.id.TimeSequenceIdGeneratorFactory;
import okjava.util.string.ToStringBuffer;

public class HasTimeSequenceIdImpl extends HasIdImpl<Long> implements HasTimeSequenceId {
    public HasTimeSequenceIdImpl() {
        super(TimeSequenceIdGeneratorFactory.timeSequenceIdGenerator().generate());
    }

    @Override
    public String toString() {
        return ToStringBuffer.of(this).
                timeSequenceId(this)
                .toString();
    }
}
