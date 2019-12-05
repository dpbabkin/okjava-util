package okjava.util.has;

import okjava.util.id.TimeSequenceIdGeneratorFactory;

public class HasTimeSequenceIdImpl extends HasIdImpl<Long> implements HasTimeSequenceId {
    public HasTimeSequenceIdImpl() {
        super(TimeSequenceIdGeneratorFactory.timeSequenceIdGenerator().generate());
    }
}
