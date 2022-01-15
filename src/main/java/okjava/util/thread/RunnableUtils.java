package okjava.util.thread;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;
import okjava.util.e.handler.atomic.ExceptionHandler;
import okjava.util.e.handler.atomic.ThrowableHandler;
import okjava.util.string.ToStringBuffer;

import java.util.function.Supplier;

import static okjava.util.NotNull.notNull;
import static okjava.util.check.Never.neverNeverCalled;


/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/1/2016
 * 21:39.
 */
@Utility
public enum RunnableUtils {
    ;

    RunnableUtils(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static Runnable wrapToString(Runnable runnable, Supplier<String> toStringSupplier) {
        return new RunnableWithSupplier(runnable, toStringSupplier);
    }

    public static Runnable wrapToString(Runnable runnable, String toString) {
        return new RunnableWithString(runnable, toString);
    }

    public static Runnable wrapRunnableExceptionHandler(Runnable delegate, ExceptionHandler<Exception> exceptionHandler) {
        return new ExceptionHandlerRunnable(delegate, exceptionHandler);
    }

    public static Runnable wrapRunnableRuntimeExceptionHandler(Runnable delegate, ExceptionHandler<RuntimeException> exceptionHandler) {
        return new RuntimeExceptionHandlerRunnable(delegate, exceptionHandler);
    }

    public static Runnable wrapRunnableThrowableHandler(Runnable delegate, ThrowableHandler<Throwable> throwableHandler) {
        return new ThrowableHandlerRunnable(delegate, throwableHandler);
    }

    private static final class ThrowableHandlerRunnable implements Runnable {
        private final Runnable runnable;
        private final ThrowableHandler<Throwable> throwableHandler;

        private ThrowableHandlerRunnable(Runnable runnable, ThrowableHandler<Throwable> throwableHandler) {
            this.runnable = notNull(runnable);
            this.throwableHandler = notNull(throwableHandler);
        }

        @Override
        public final void run() {
            try {
                runnable.run();
            } catch (Throwable e) {
                throwableHandler.accept(e);
            }
        }
        @Override
        public String toString() {
            return ToStringBuffer.of(this).addWithClass("runnable", runnable).toString();
        }
    }

    private static final class RuntimeExceptionHandlerRunnable implements Runnable {
        private final Runnable runnable;
        private final ExceptionHandler<RuntimeException> exceptionHandler;

        private RuntimeExceptionHandlerRunnable(Runnable runnable, ExceptionHandler<RuntimeException> exceptionHandler) {
            this.runnable = notNull(runnable);
            this.exceptionHandler = notNull(exceptionHandler);
        }

        @Override
        public final void run() {
            try {
                runnable.run();
            } catch (RuntimeException e) {
                exceptionHandler.accept(e);
            }
        }
        @Override
        public String toString() {
            return ToStringBuffer.of(this).addWithClass("runnable", runnable).toString();
        }
    }

    private static final class ExceptionHandlerRunnable implements Runnable {
        private final Runnable runnable;
        private final ExceptionHandler<Exception> exceptionHandler;

        private ExceptionHandlerRunnable(Runnable runnable, ExceptionHandler<Exception> exceptionHandler) {
            this.runnable = notNull(runnable);
            this.exceptionHandler = notNull(exceptionHandler);
        }

        @Override
        public final void run() {
            try {
                runnable.run();
            } catch (Exception e) {
                exceptionHandler.accept(e);
            }
        }

        @Override
        public String toString() {
            return ToStringBuffer.of(this).addWithClass("runnable", runnable).toString();
        }
    }

    private static abstract class ABRunnable implements Runnable {
        private final Runnable runnable;

        public ABRunnable(Runnable runnable) {
            this.runnable = notNull(runnable);
        }

        @Override
        public final void run() {
            runnable.run();
        }

        @Override
        public String toString() {
            return getToString();
        }

        protected abstract String getToString();
    }

    private static final class RunnableWithSupplier extends ABRunnable {

        private final Supplier<String> supplier;

        private RunnableWithSupplier(Runnable runnable, Supplier<String> supplier) {
            super(runnable);
            this.supplier = notNull(supplier);
        }

        @Override
        protected String getToString() {
            return supplier.get();
        }
    }

    private static final class RunnableWithString extends ABRunnable {

        private final String toString;

        private RunnableWithString(Runnable runnable, String toString) {
            super(runnable);
            this.toString = notNull(toString);
        }

        @Override
        protected String getToString() {
            return toString;
        }
    }
}
