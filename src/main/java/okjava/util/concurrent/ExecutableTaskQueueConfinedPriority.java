package okjava.util.concurrent;

import java.util.concurrent.Executor;

import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 11/25/2016
 * 19:49.
 */
public final class ExecutableTaskQueueConfinedPriority<P> implements PriorityExecutor<P> {

    private final TaskQueueConfinedPriority<P> taskQueueConfinedPriority;
    private final Executor executor;

    private ExecutableTaskQueueConfinedPriority(Executor executor, TaskQueueConfinedPriority<P> taskQueueConfinedPriority) {
        this.executor = notNull(executor);
        this.taskQueueConfinedPriority = notNull(taskQueueConfinedPriority);
    }

    public static <P> ExecutableTaskQueueConfinedPriority<P> create(Executor executor, TaskQueueConfinedPriority<P> taskQueueConfinedPriority) {
        return new ExecutableTaskQueueConfinedPriority<P>(executor, taskQueueConfinedPriority);
    }

    public void execute(PriorityRunnable<P> priorityRunnable) {
        Runnable runnable = taskQueueConfinedPriority.queueTask(priorityRunnable);
        executor.execute(runnable);
    }
}
