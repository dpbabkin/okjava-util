package okjava.util.concurrent;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 11/26/2016
 * 16:27.
 */
public interface PriorityRunnable<P> extends Runnable {

    static PriorityRunnable<Boolean> fromRunnable(Runnable runnable) {
        return fromRunnable(true, runnable);
    }

    static <P> PriorityRunnable<P> fromRunnable(P priority, Runnable runnable) {
        return new PriorityRunnable<P>() {
            @Override
            public P getPriority() {
                return priority;
            }

            @Override
            public void run() {
                runnable.run();
            }
        };
    }

    P getPriority();
}
