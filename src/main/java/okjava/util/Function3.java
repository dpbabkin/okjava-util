package okjava.util;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/24/2016
 * 22:53.
 */
@FunctionalInterface
public interface Function3<P1, P2, P3, R> {
    R apply(P1 p1, P2 p2, P3 p3);
}
