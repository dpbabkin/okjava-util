package okjava.util.thread;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.util.function.Supplier;

import static okjava.util.NotNull.notNull;
import static okjava.util.check.Never.neverNeverCalled;


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
        return new RunnableWithSupplier(runnable, toStringSupplier);
    }

    public static Runnable wrapToString(Runnable runnable, String toString) {
        return new RunnableWithString(runnable, toString);
    }

    private static abstract class ABRunnable implements Runnable {
        private final Runnable runnable;

        public ABRunnable(Runnable runnable) {
            this.runnable = notNull(runnable);
        }

        @Override
        public void run() {
            runnable.run();
        }

        @Override
        public String toString() {
            return getToString();
        }

        protected abstract String getToString();
    }

    private static final class RunnableWithSupplier extends ABRunnable {

        private final Supplier<String> supplier;

        private RunnableWithSupplier(Runnable runnable, Supplier<String> supplier) {
            super(runnable);
            this.supplier = notNull(supplier);
        }

        @Override
        protected String getToString() {
            return supplier.get();
        }
    }

    private static final class RunnableWithString extends ABRunnable {

        private final String toString;

        private RunnableWithString(Runnable runnable, String toString) {
            super(runnable);
            this.toString = notNull(toString);
        }

        @Override
        protected String getToString() {
            return toString;
        }
    }
}
