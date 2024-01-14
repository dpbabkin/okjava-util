package okjava.util.thread;

import java.util.concurrent.Executor;

import static okjava.util.NotNull.notNull;

class OkExecutorImpl implements OkExecutor {

    private final Executor executorDelegate;

    private OkExecutorImpl(Executor executorDelegate) {
        this.executorDelegate = notNull(executorDelegate);
    }

    static OkExecutor create(Executor executorDelegate) {
        return new OkExecutorImpl(executorDelegate);
    }

    /**
     * Executes the given command at some time in the future.  The command
     * may execute in a new thread, in a pooled thread, or in the calling
     * thread, at the discretion of the {@code Executor} implementation.
     *
     * @param command the runnable task accepted for execution
     * @throws NullPointerException if command is null
     */
    @Override
    public void execute(Runnable command) {
        executorDelegate.execute(command);
    }
}
