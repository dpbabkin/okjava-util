package okjava.util.e;

import static java.util.Objects.requireNonNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         10/3/2015
 *         01:33.
 */
@FunctionalInterface
public interface ERunnable<E extends Exception> {

    static ERunnable<RuntimeException> delegate(Runnable runnable) {
        requireNonNull(runnable);
        return runnable::run;
    }

    void run() throws E;
}
