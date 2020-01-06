package okjava.util.has;

import okjava.util.id.timesequence.LongTimeSequenceIdUtils;
import okjava.util.id.timesequence.TimeSequenceId;
import okjava.util.id.timesequence.TimeSequenceIdFactory;

import java.time.LocalDateTime;

import static okjava.util.id.timesequence.LongTimeSequenceIdUtils.convertMillisToLocalDateTime;
import static okjava.util.id.timesequence.LongTimeSequenceIdUtils.fetchTime;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/26/2019
 * 17:42.
 */
public interface HasTimeSequenceId extends HasLongId {

    @Override
    default String getStringId() {
        return LongTimeSequenceIdUtils.format(getId());
    }

    default LocalDateTime getCreationTime() {
        return convertMillisToLocalDateTime(getCreationTimeAsLong());
    }

    default long getCreationTimeAsLong() {
        return fetchTime(getId());
    }

    default TimeSequenceId getTimeSequence() {
        return TimeSequenceIdFactory.timeSequenceIdFactory().fromLong(getId());
    }
}
