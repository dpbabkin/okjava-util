package okjava.util.e.handler;

import okjava.util.e.handler.atomic.ThrowableHandler;

public final class RuntimeUnhandledExceptionWrapper extends RuntimeException {
    <C extends ThrowableHandler> RuntimeUnhandledExceptionWrapper(Class<C> clazz, Throwable throwable) {
        super(clazz.getName(), throwable);
    }
}
