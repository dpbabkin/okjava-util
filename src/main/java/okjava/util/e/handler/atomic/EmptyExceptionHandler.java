package okjava.util.e.handler.atomic;

import okjava.util.annotation.Singleton;

import static okjava.util.check.Once.calledOnce;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 13.01.2022 - 21:02.
 */
@Singleton
class EmptyExceptionHandler implements ExceptionHandler<Exception> {

    private static final ExceptionHandler<Exception> INSTANCE = new EmptyExceptionHandler();

    private EmptyExceptionHandler() {
        calledOnce(this.getClass());
    }

    static ExceptionHandler<Exception> create() {
        return INSTANCE;
    }

    static ExceptionHandler<Exception> emptyExceptionHandler() {
        return INSTANCE;
    }

    public static ExceptionHandler<Exception> i() {
        return INSTANCE;
    }

    @Override
    public void accept(Exception e) {
        if (e instanceof RuntimeException) {
            throw (RuntimeException) e;
        }
        throw new RuntimeException(e);
    }
}
