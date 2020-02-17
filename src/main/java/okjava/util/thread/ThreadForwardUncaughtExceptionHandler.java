package okjava.util.thread;

import okjava.util.annotation.Singleton;

import static okjava.util.check.Once.calledOnce;

@Singleton
final class ThreadForwardUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static Thread.UncaughtExceptionHandler INSTANCE = new ThreadForwardUncaughtExceptionHandler();

    private ThreadForwardUncaughtExceptionHandler() {
        calledOnce(this.getClass());
    }

    static Thread.UncaughtExceptionHandler threadForwardUncaughtExceptionHandler() {
        return INSTANCE;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Thread.UncaughtExceptionHandler tueh = t.getUncaughtExceptionHandler();
        if (tueh == null) {
            tueh = Thread.getDefaultUncaughtExceptionHandler();
        }
        if (tueh == null) {
            tueh = ExceptionHandler.create();
        }
        tueh.uncaughtException(t, e);
    }
}
