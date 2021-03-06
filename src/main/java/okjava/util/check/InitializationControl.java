package okjava.util.check;

import okjava.util.annotation.Utility;

import java.util.UUID;

import static okjava.util.check.Never.fail;
import static okjava.util.check.Never.neverNeverCalled;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 9/17/2016
 * 14:44.
 */
@Utility
public enum InitializationControl {
    ;

    private static final String UNIQUE_ID = UUID.randomUUID().toString();

    InitializationControl(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static void checkForClass(Class<?> clazz, ControlObject control) {
        if (!generateIdForClass(clazz, UNIQUE_ID).equals(control.getId())) {
            fail("wrong initialization argument for class=" + clazz);
        }
    }

    public static ControlObject generateForClass(Class<?> clazz) {
        return ControlObject.create(generateIdForClass(clazz, UNIQUE_ID));
    }

    private static String generateIdForClass(Class<?> clazz, String uniqueId) {
        return uniqueId + ".|." + InitializationControl.class + ".|." + clazz.toString();
    }
}
