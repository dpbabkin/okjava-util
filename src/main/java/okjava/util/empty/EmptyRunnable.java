package okjava.util.empty;

import static okjava.util.check.Once.calledOnce;

import okjava.util.annotation.Singleton;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 9/17/2016
 * 14:40.
 */
@Singleton
public  final class EmptyRunnable implements Runnable {

    private static EmptyRunnable INSTANCE = new EmptyRunnable();

    private EmptyRunnable() {
        calledOnce(EmptyRunnable.class);
    }

    public static EmptyRunnable create() {
        return INSTANCE;
    }

    public static EmptyRunnable emptyRunnable() {
        return create();
    }

    public static EmptyRunnable empty() {
        return create();
    }

    @Override
    public void run() {
    }

    @Override
    public String toString() {
        return EmptyRunnable.class.getSimpleName();
    }
}
