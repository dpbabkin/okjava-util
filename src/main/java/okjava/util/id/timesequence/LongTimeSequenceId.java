package okjava.util.id.timesequence;

import okjava.util.has.HasLongId;

public interface LongTimeSequenceId extends TimeSequenceId, HasLongId {

    long getRawLong();
}
