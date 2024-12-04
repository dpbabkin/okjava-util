package okjava.util.thread;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;
import okjava.util.e.handler.atomic.ExceptionHandler;

import java.util.concurrent.Executor;

import static okjava.util.thread.RunnableUtils.wrapRunnableExceptionHandler;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 14.01.2022 - 19:10.
 */
@Utility
public class ExecutorUtils {
    ;

    ExecutorUtils(@SuppressWarnings("unused") Never never) {
        Never.neverNeverCalled();
    }


    public static Executor wrapExceptionHandler(ExceptionHandler<Exception> exceptionHandler, Executor executor) {
        return command -> executor.execute(wrapRunnableExceptionHandler(command, exceptionHandler));
    }
}
