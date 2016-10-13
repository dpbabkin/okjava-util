package okjava.util.check;

import static okjava.util.check.Never.fail;
import static okjava.util.check.Never.neverCalled;

import okjava.util.annotation.Utility;

import java.util.UUID;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         9/17/2016
 *         14:44.
 */
@Utility
final class InitializationControl {

    private static final String UNIQUE_ID = UUID.randomUUID().toString();

    private InitializationControl() {
        neverCalled();
    }

    static void checkForClass(Class<?> clazz, String uniqueId) {
        if (!generateIdForClass(clazz, UNIQUE_ID).equals(uniqueId)) {
            fail("wrong initialization argument for class=" + clazz);
        }
    }

    static String getIdForClass(Class<?> clazz) {
        return generateIdForClass(clazz, UNIQUE_ID);
    }

    private static String generateIdForClass(Class<?> clazz, String uniqueId) {
        return uniqueId + ".|." + InitializationControl.class + ".|." + clazz.toString();
    }
}
