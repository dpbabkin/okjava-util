package okjava.util.e.handler;

import okjava.util.e.handler.atomic.ErrorHandler;
import okjava.util.e.handler.atomic.ExceptionHandler;
import okjava.util.e.handler.atomic.RuntimeExceptionHandler;
import okjava.util.e.handler.atomic.ThrowableHandler;

import java.util.function.Consumer;

public interface Handler {
    RuntimeExceptionHandler<RuntimeException> getRuntimeExceptionHandler();

    ExceptionHandler<Exception> getExceptionHandler();

    ErrorHandler<Error> getErrorHandler();

    ThrowableHandler<Throwable> getThrowableHandler();

    default Consumer<Runnable> createConsumer() {
        return createConsumer(this);
    }

    static Consumer<Runnable> createConsumer(Handler handler) {
        Consumer<Runnable> consumer = runnable -> {
            try {
                runnable.run();
            } catch (RuntimeException runtimeException) {
                handler.getRuntimeExceptionHandler().accept(runtimeException);
            } catch (Exception exception) {
                handler.getExceptionHandler().accept(exception);
            } catch (Error error) {
                handler.getErrorHandler().accept(error);
            } catch (Throwable throwable) {
                handler.getThrowableHandler().accept(throwable);
            }
        };
        return consumer;
    }
}
