package okjava.util.id.timesequence;

import okjava.util.has.HasLongId;
import okjava.util.id.timesequence.TimeSequenceId;

public interface LongTimeSequenceId extends TimeSequenceId, HasLongId {

    long getRawLong();
}
