package okjava.util.has;

import okjava.util.id.LongTimeSequenceId;

import java.time.Instant;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/26/2019
 * 17:42.
 */
@Deprecated //use HasTimeSequenceId
public interface HasLongTimeSequenceId extends HasId<LongTimeSequenceId> {

    @Override
    default String getStringId() {
        return getTimeSequence().toString();
    }

    default Instant getCreationLocalDateTime() {
        return Instant.ofEpochMilli(getTimeSequence().getTime());
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
