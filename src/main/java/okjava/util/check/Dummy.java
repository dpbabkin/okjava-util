package okjava.util.check;

import static okjava.util.check.InitializationControl.checkForClass;
import static okjava.util.check.InitializationControl.generateForClass;
import static okjava.util.check.Once.calledOnce;

import okjava.util.annotation.Singleton;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 9/17/2016
 * 14:44.
 */
@Singleton
public final class Dummy {

    private static Dummy DUMMY = new Dummy(generateForClass(Dummy.class));

    private Dummy(ControlObject uniqueId) {
        checkForClass(this.getClass(), uniqueId);
        calledOnce(this.getClass());
    }

    public static Dummy create() {
        return DUMMY;
    }
}
