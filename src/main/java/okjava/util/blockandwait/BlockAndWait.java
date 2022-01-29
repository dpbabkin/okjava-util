package okjava.util.blockandwait;

import okjava.util.condition.Cancellable;
import okjava.util.condition.Updatable;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public interface BlockAndWait extends Cancellable, Updatable {

    <V> V await(Supplier<V> isEventHappened, long time) throws InterruptedException;

    default <V> V await(Supplier<V> isEventHappened) throws InterruptedException {
        return await(isEventHappened, Constants.WAIT_FOREVER);
    }

    default <V> V await(Supplier<V> isEventHappened, long time, TimeUnit timeUnit) throws InterruptedException {
        return await(isEventHappened, timeUnit.toMillis(time));
    }
}
