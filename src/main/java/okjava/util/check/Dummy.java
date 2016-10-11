package okjava.util.check;

import static okjava.util.check.Once.calledOnce;

import okjava.util.annotation.Singleton;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         9/17/2016
 *         14:44.
 */
@Singleton
public final class Dummy {

    private static Dummy DUMMY = new Dummy(InitializationControl.getIdForClass(Dummy.class));

    private Dummy(String uniqueId) {
        InitializationControl.checkForClass(Dummy.class, uniqueId);
        calledOnce(Dummy.class);
    }

    public static Dummy create() {
        return DUMMY;
    }
}
