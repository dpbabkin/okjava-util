package okjava.util.concurrent;

import java.util.concurrent.Executor;
import java.util.function.Consumer;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 11/25/2016
 * 19:57.
 */
public interface PriorityExecutorSimple extends Executor, Consumer<Runnable> {

    void executeFirst(Runnable runnable);

    void queueTaskLast(Runnable runnable);

    @Override
    default void execute(Runnable runnable) {
        queueTaskLast(runnable);
    }

    @Override
    default void accept(Runnable runnable) {
        execute(runnable);
    }
}
