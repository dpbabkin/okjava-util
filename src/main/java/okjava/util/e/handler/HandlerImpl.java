package okjava.util.e.handler;

import okjava.util.e.handler.atomic.ErrorHandler;
import okjava.util.e.handler.atomic.ExceptionHandler;
import okjava.util.e.handler.atomic.RuntimeExceptionHandler;
import okjava.util.e.handler.atomic.ThrowableHandler;
import okjava.util.string.ToStringBuffer;

import static okjava.util.NotNull.notNull;

final class HandlerImpl implements Handler {
    private final RuntimeExceptionHandler<RuntimeException> runtimeExceptionHandler;

    private final ExceptionHandler<Exception> exceptionHandler;

    private final ErrorHandler<Error> errorHandler;

    private final ThrowableHandler<Throwable> throwableHandler;

    static Handler create(RuntimeExceptionHandler<RuntimeException> runtimeExceptionHandler, ExceptionHandler<Exception> exceptionHandler, ErrorHandler<Error> errorHandler, ThrowableHandler<Throwable> throwableHandler) {
        return new HandlerImpl(runtimeExceptionHandler, exceptionHandler, errorHandler, throwableHandler);
    }

    private HandlerImpl(RuntimeExceptionHandler<RuntimeException> runtimeExceptionHandler, ExceptionHandler<Exception> exceptionHandler, ErrorHandler<Error> errorHandler, ThrowableHandler<Throwable> throwableHandler) {
        this.runtimeExceptionHandler = notNull(runtimeExceptionHandler);
        this.exceptionHandler = notNull(exceptionHandler);
        this.errorHandler = notNull(errorHandler);
        this.throwableHandler = notNull(throwableHandler);
    }

    @Override
    public RuntimeExceptionHandler<RuntimeException> getRuntimeExceptionHandler() {
        return runtimeExceptionHandler;
    }

    @Override
    public ExceptionHandler<Exception> getExceptionHandler() {
        return exceptionHandler;
    }

    @Override
    public ErrorHandler<Error> getErrorHandler() {
        return errorHandler;
    }

    @Override
    public ThrowableHandler<Throwable> getThrowableHandler() {
        return throwableHandler;
    }

    @Override
    public String toString() {
        return ToStringBuffer.of(this)
                .add("runtimeExceptionHandler", runtimeExceptionHandler)
                .add("exceptionHandler", exceptionHandler)
                .add("errorHandler", errorHandler)
                .add("throwableHandler", throwableHandler)
                .toString();
    }
}
