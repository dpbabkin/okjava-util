package okjava.util.concurrent;

import okjava.util.empty.EmptyRunnable;

import java.util.concurrent.Executor;

import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 11/25/2016
 * 19:49.
 */
public final class ExecutableTaskQueueConfined implements TwoSideExecutor {

    private final TaskQueueConfined taskQueueConfined;
    private final Executor executor;
    private final Runnable outerRunnable;
    private final Runnable innerRunnable;

    private ExecutableTaskQueueConfined(Executor executor) {
        this.executor = notNull(executor);
        this.taskQueueConfined = TaskQueueConfined.create();
        this.innerRunnable = this.taskQueueConfined.queueTaskFirst(EmptyRunnable.emptyRunnable());
        assert this.taskQueueConfined.getQueueSize() == 1 : this.taskQueueConfined.getQueueSize();
        this.innerRunnable.run();
        assert this.taskQueueConfined.getQueueSize() == 0 : this.taskQueueConfined.getQueueSize();
        this.outerRunnable = new OuterRunnable();
    }

    private final class OuterRunnable implements Runnable {

        @Override
        public void run() {
            boolean isFinishSuccessfully = false;
            try {
                ExecutableTaskQueueConfined.this.innerRunnable.run();
                isFinishSuccessfully = true;
            } finally {
                if (isFinishSuccessfully == false) {
                    // Throwable had been thrown inside try{} block. Instead of catch that Throwable and sneakyThrow again, use status boolean variable.
                    executor.execute(ExecutableTaskQueueConfined.this.outerRunnable);
                }
            }
        }
    }

    public static ExecutableTaskQueueConfined create(Executor executor) {
        return new ExecutableTaskQueueConfined(executor);
    }

    @Override
    public void executeFirst(Runnable runnable) {
        Runnable r = taskQueueConfined.queueTaskFirst(runnable);
        assert r == this.innerRunnable;
        executor.execute(this.outerRunnable);
    }

    @Override
    public void queueTaskLast(Runnable runnable) {
        Runnable r = taskQueueConfined.queueTaskLast(runnable);
        assert r == this.innerRunnable;
        executor.execute(this.outerRunnable);
    }
}
