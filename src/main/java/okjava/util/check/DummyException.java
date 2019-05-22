package okjava.util.check;

import static okjava.util.check.Once.calledOnce;

import okjava.util.annotation.Singleton;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 7/26/2016
 * 23:11.
 */
@Singleton
public final class DummyException extends Exception {

    private static DummyException INSTANCE = new DummyException(Dummy.create());

    private DummyException(Dummy dummy) {
        calledOnce(DummyException.class);
    }

    public static DummyException create() {
        return INSTANCE;
    }
}
