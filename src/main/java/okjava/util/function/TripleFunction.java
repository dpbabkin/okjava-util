package okjava.util.function;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 1/8/2017
 * 21:51.
 */
@FunctionalInterface
public interface TripleFunction<V1, V2, V3, R> {

    R apply(V1 first, V2 second, V2 third);
}
