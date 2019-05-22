package okjava.util.concurrent;

import static okjava.util.NotNull.notNull;

import java.util.concurrent.Executor;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 11/25/2016
 * 19:49.
 */
public final class ExecutableTaskQueueConfined implements PriorityExecutorSimple {

    private final TaskQueueConfined taskQueueConfined;
    private final Executor executor;

    private ExecutableTaskQueueConfined(Executor executor) {
        this.executor = notNull(executor);
        this.taskQueueConfined = new TaskQueueConfined();
    }

    public static ExecutableTaskQueueConfined create(Executor executor) {
        return new ExecutableTaskQueueConfined(executor);
    }

    @Override
    public void executeFirst(Runnable runnable) {
        Runnable r = taskQueueConfined.queueTaskFirst(runnable);
        executor.execute(r);
    }

    @Override
    public void queueTaskLast(Runnable runnable) {
        Runnable r = taskQueueConfined.queueTaskLast(runnable);
        executor.execute(r);
    }
}
