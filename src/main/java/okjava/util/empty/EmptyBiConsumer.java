package okjava.util.empty;

import static okjava.util.check.Never.neverNeverCalled;
import static okjava.util.check.Once.calledOnce;

import okjava.util.annotation.Utility;
import okjava.util.check.Dummy;
import okjava.util.check.Never;

import java.util.function.BiConsumer;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         9/17/2016
 *         14:40.
 */
@Utility
public enum EmptyBiConsumer {
    ;

    private static BiConsumerImpl<?, ?> INSTANCE = new BiConsumerImpl<>(Dummy.create());

    EmptyBiConsumer(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    @SuppressWarnings("unchecked") // safe as implementation is empty.
    public static <T, U> BiConsumer<T, U> create() {
        return (BiConsumer<T, U>) INSTANCE;
    }

    public static <T, U> BiConsumer<T, U> emptyBiConsumer() {
        return create();
    }

    public static <T, U> BiConsumer<T, U> empty() {
        return create();
    }

    private static final class BiConsumerImpl<T, U> implements BiConsumer<T, U> {

        private BiConsumerImpl(Dummy dummy) {
            calledOnce(BiConsumerImpl.class);
        }

        @Override
        public void accept(T t, U u) {
        }

        @Override
        public String toString() {
            return "EmptyBiConsumer";
        }
    }
}
