package okjava.util;

import static okjava.util.check.Never.neverNeverCalled;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.util.function.Supplier;


/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         6/1/2016
 *         21:39.
 */
@Utility
public enum SupplierUtils {
    ;

    SupplierUtils(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static <V> Supplier<V> wrapToString(Supplier<V> supplier, Supplier<String> toStringSupplier) {
        return new Supplier() {
            @Override
            public V get() {
                return supplier.get();
            }

            @Override
            public String toString() {
                return toStringSupplier.get();
            }
        };
    }
}
