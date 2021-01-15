package okjava.util.id.timesequence;

import okjava.util.datetime.DateTimeFormatUtils;

import static okjava.util.id.timesequence.TimeSequenceIdComparator.timeSequenceIdComparator;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 20:25.
 */
public interface TimeSequenceId extends Comparable<TimeSequenceId> {
    long getTime();

    long getSequence();

    @Override
    int compareTo(@Nonnull TimeSequenceId other);

    default LocalDateTime getCreationLocalDateTime() {
        return DateTimeFormatUtils.convertMillisToLocalDateTime(getTime());
    }
}
