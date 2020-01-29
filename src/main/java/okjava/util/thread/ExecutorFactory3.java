package okjava.util.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;

public interface ExecutorFactory3 {

    Executor createExecutor();

    Executor createLowPriorityExecutor();

    ScheduledExecutorService createScheduledExecutor();
}
