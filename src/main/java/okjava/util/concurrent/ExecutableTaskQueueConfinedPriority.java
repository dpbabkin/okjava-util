package okjava.util.concurrent;

import static okjava.util.NotNull.notNull;

import java.util.concurrent.Executor;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         11/25/2016
 *         19:49.
 */
public final class ExecutableTaskQueueConfinedPriority<P> implements PriorityExecutor<P> {

    private final TaskQueueConfinedPriority<P> taskQueueConfinedPriority;
    private final Executor executor;

    public static <P> ExecutableTaskQueueConfinedPriority<P> create(Executor executor, TaskQueueConfinedPriority<P> taskQueueConfinedPriority) {
        return new ExecutableTaskQueueConfinedPriority<P>(executor, taskQueueConfinedPriority);
    }

    private ExecutableTaskQueueConfinedPriority(Executor executor, TaskQueueConfinedPriority<P> taskQueueConfinedPriority) {
        this.executor = notNull(executor);
        this.taskQueueConfinedPriority = notNull(taskQueueConfinedPriority);
    }

    public void execute(PriorityRunnable<P> priorityRunnable) {
        Runnable runnable = taskQueueConfinedPriority.queueTask(priorityRunnable);
        executor.execute(runnable);
    }
}
