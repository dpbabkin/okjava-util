package okjava.util.check;

import okjava.util.annotation.Singleton;

import static okjava.util.check.Once.calledOnce;

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
