package okjava.util.id.timesequence;

import javax.annotation.Nonnull;
import java.time.Instant;

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

    default Instant getCreationInstant() {
        return Instant.ofEpochMilli(getTime());
    }
}
