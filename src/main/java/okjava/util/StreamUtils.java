package okjava.util;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;


/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         6/1/2016
 *         21:39.
 */
@Utility
public final class StreamUtils {
    private StreamUtils(Never never) {
        Never.neverCalled();
    }

    public static <T> Stream<T> toStream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
