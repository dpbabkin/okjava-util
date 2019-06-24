package okjava.util.e;

import static java.util.Objects.requireNonNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 10/3/2015
 * 01:33.
 */
@FunctionalInterface
public interface E4Runnable<E1 extends Exception, E2 extends Exception, E3 extends Exception, E4 extends Exception> {

    static E4Runnable<RuntimeException, RuntimeException, RuntimeException, RuntimeException> delegate(Runnable runnable) {
        requireNonNull(runnable);
        return runnable::run;
    }

    void run() throws E1, E2, E3, E4;
}
