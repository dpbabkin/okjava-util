package okjava.util.has;

import okjava.util.check.MathCheck;
import okjava.util.id.TimeSequenceIdGeneratorFactory;
import okjava.util.string.ToStringBuffer;

public class HasTimeSequenceIdImpl extends HasIdImpl<Long> implements HasTimeSequenceId {

    public HasTimeSequenceIdImpl(long id) {
        super(MathCheck.positive(id));
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
