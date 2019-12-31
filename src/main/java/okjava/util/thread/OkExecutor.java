package okjava.util.thread;

import okjava.util.RunnableUtils;

import java.util.concurrent.Executor;
import java.util.function.Supplier;

public interface OkExecutor extends Executor {

    default void execute(Runnable command, String toString) {
        execute(RunnableUtils.wrapToString(command, toString));
    }

    default void execute(Runnable command, Supplier<String> toString) {
        execute(RunnableUtils.wrapToString(command, toString));
    }
}
