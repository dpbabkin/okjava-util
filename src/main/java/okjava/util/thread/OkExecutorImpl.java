package okjava.util.thread;

import java.util.concurrent.Executor;

import static okjava.util.NotNull.notNull;

public class OkExecutorImpl implements OkExecutor {

    private final Executor executorDelegate;

    private OkExecutorImpl(Executor executorDelegate) {
        this.executorDelegate = notNull(executorDelegate);
    }

    public static OkExecutor create(Executor executorDelegate) {
        return new OkExecutorImpl(executorDelegate);
    }

    @Override
    public void execute(Runnable command) {
        executorDelegate.execute(command);
    }
}
