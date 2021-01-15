package okjava.util.has;

import okjava.util.datetime.DateTimeFormatUtils;
import okjava.util.id.LongTimeSequenceId;
import okjava.util.id.timesequence.TimeSequenceId;

import java.time.LocalDateTime;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/26/2019
 * 17:42.
 */
public interface HasTimeSequenceId extends HasId<TimeSequenceId> {

//    @Override
//    default String getStringId() {
//        return getId().toString();
//    }

    //default LocalDateTime getCreationLocalDateTime() {
    //    return DateTimeFormatUtils.convertMillisToLocalDateTime(getId().getTime());
    //}

//    //default long getCreationTimeAsLong() {
//        return
//
//    getTimeSequence().
//
//    getTime();
//
//}

    //default TimeSequenceId getTimeSequence() {
    //    return getId();
    //}
}
