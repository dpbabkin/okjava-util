package okjava.util;

import static okjava.util.check.Never.neverNeverCalled;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.util.function.Supplier;


/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/1/2016
 * 21:39.
 */
@Utility
public enum RunnableUtils {
    ;

    RunnableUtils(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static Runnable wrapToString(Runnable runnable, Supplier<String> toStringSupplier) {
        return new Runnable() {
            @Override
            public void run() {
                runnable.run();
            }

            @Override
            public String toString() {
                return toStringSupplier.get();
            }
        };
    }

    public static Runnable wrapToString(Runnable runnable, String toString) {
        return new Runnable() {
            @Override
            public void run() {
                runnable.run();
            }

            @Override
            public String toString() {
                return toString;
            }
        };
    }
}
