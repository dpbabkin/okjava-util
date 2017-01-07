package okjava.util.concurrent;

import java.util.concurrent.Executor;
import java.util.function.Consumer;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         11/25/2016
 *         19:57.
 */
public interface PriorityExecutor<P> extends Consumer<PriorityRunnable<P>> {

    void execute(PriorityRunnable<P> priorityRunnable);

    @Override
    default void accept(PriorityRunnable<P> priorityRunnable) {
        execute(priorityRunnable);
    }
}
