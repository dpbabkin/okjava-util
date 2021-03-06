package okjava.util.function;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 1/8/2017
 * 21:51.
 */
@FunctionalInterface
public interface IntBiFunction<R> {

    R apply(int first, int second);
}
