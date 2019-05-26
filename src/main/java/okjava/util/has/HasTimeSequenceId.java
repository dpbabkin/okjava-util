package okjava.util.has;

import okjava.util.id.timesequence.LongTimeSequenceIdUtils;
import okjava.util.id.timesequence.TimeSequenceId;
import okjava.util.id.timesequence.TimeSequenceIdFactory;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/26/2019
 * 17:42.
 */
public interface HasTimeSequenceId extends HasLongId {

    @Override
    default String getStringId() {
        return LongTimeSequenceIdUtils.formatId(getId());
    }

    default TimeSequenceId getTimeSequence() {
        return TimeSequenceIdFactory.timeSequenceIdFactory().fromLong(getId());
    }
}
