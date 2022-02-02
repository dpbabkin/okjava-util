package okjava.util.condition;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 29.01.2022 - 11:48.
 */
@Utility
public enum WaiterFactories {
    ;

    WaiterFactories(@SuppressWarnings("unused") Never never) {
        Never.neverNeverCalled(); //Settings/Editor/Live Templates/Java/utl
    }


    public static WaiterFactory create() {
        return WaiterFactoryImpl.create();
    }

    public static WaiterFactory withoutDefaultPoll() {
        return create().withoutDefaultPoll();
    }

    public static WaiterFactory withPoll(long poolInterval) {
        return create().withPoll(poolInterval);
    }
}
