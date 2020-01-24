package okjava.util.has;

import okjava.util.datetime.DateTimeFormatUtils;
import okjava.util.id.LongTimeSequenceId;

import java.time.LocalDateTime;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/26/2019
 * 17:42.
 */
public interface HasLongTimeSequenceId extends HasId<LongTimeSequenceId> {

    @Override
    default String getStringId() {
        return getTimeSequence().toString();
    }

    default LocalDateTime getCreationLocalDateTime() {
        return DateTimeFormatUtils.convertMillisToLocalDateTime(getTimeSequence().getTime());
    }

//    //default long getCreationTimeAsLong() {
//        return
//
//    getTimeSequence().
//
//    getTime();
//
//}

    default LongTimeSequenceId getTimeSequence() {
        return getId();
    }
}
