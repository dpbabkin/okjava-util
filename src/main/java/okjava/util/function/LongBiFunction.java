package okjava.util.function;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         1/8/2017
 *         21:51.
 */
@FunctionalInterface
public interface LongBiFunction<R> {

    R apply(long first, long second);
}
