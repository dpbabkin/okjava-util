package okjava.util.function;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 4/20/2018
 * 19:24.
 */
public interface BiSupplier<A, B> {
    A getA();

    B getB();
}

