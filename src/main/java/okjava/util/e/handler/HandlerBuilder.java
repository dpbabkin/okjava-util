package okjava.util.e.handler;

import okjava.util.e.handler.atomic.ErrorHandler;
import okjava.util.e.handler.atomic.ExceptionHandler;
import okjava.util.e.handler.atomic.RuntimeExceptionHandler;
import okjava.util.e.handler.atomic.ThrowableHandler;
import okjava.util.string.ToStringBuffer;

import static okjava.util.NotNull.notNull;

public class HandlerBuilder {
    private RuntimeExceptionHandler<RuntimeException> runtimeExceptionHandler = e -> {
        throw e;
    };

    private ExceptionHandler<Exception> exceptionHandler = e -> {
        throw new RuntimeUnhandledExceptionWrapper(ExceptionHandler.class, e);
    };

    private ErrorHandler<Error> errorHandler = e -> {
        throw e;
    };

    private ThrowableHandler<Throwable> throwableHandler = e -> {
        if(e instanceof RuntimeException){
            throw (RuntimeException)e;
        }
        if(e instanceof Error){
            throw (Error)e;
        }
        throw new RuntimeUnhandledExceptionWrapper(ThrowableHandler.class, e);
    };

    public static HandlerBuilder handlerBuilder() {
        return new HandlerBuilder();
    }

    private HandlerBuilder() {
    }

    public HandlerBuilder withRuntimeExceptionHandler(RuntimeExceptionHandler<RuntimeException> runtimeExceptionHandler) {
        this.runtimeExceptionHandler = notNull(runtimeExceptionHandler);
        return this;
    }

    public HandlerBuilder withExceptionHandler(ExceptionHandler<Exception> exceptionHandler) {
        this.exceptionHandler = notNull(exceptionHandler);
        return this;
    }

    public HandlerBuilder withErrorHandler(ErrorHandler<Error> errorHandler) {
        this.errorHandler = notNull(errorHandler);
        return this;
    }

    public HandlerBuilder withThrowableHandler(ThrowableHandler<Throwable> throwableHandler) {
        this.throwableHandler = notNull(throwableHandler);
        return this;
    }

    public HandlerBuilder withAll(ThrowableHandler<Throwable> throwableHandler) {
        return withRuntimeExceptionHandler(throwableHandler::accept)
                .withExceptionHandler(throwableHandler::accept)
                .withErrorHandler(throwableHandler::accept)
                .withThrowableHandler(throwableHandler);
    }

    public Handler build() {
        return HandlerImpl.create(runtimeExceptionHandler, exceptionHandler, errorHandler, throwableHandler);
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
