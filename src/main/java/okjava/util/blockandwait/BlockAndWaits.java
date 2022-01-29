package okjava.util.blockandwait;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import static okjava.util.check.Never.neverNeverCalled;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/5/2019
 * 09:38.
 */
@Utility
public enum BlockAndWaits {
    ;

    BlockAndWaits(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static BlockAndWait create() {
        return BlockAndWaitMain.create(); //TODO move to same package.
    }

}
