package okjava.util.poller;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.util.function.Supplier;

import static okjava.util.check.Never.neverNeverCalled;

@Utility
public class ValueHolderFactory {
    ;

    ValueHolderFactory(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static <V> UpdatableValueHolder<V> create(Supplier<V> supplier) {
        return UpdatableValueHolderImpl.create(supplier);
    }

    public static <V> ConsumerValueHolder<V> create(V value) {
        return ConsumerValueHolderImpl.create(value);
    }
}
