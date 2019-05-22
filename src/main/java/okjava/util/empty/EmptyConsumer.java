package okjava.util.empty;

import static okjava.util.check.Never.neverNeverCalled;
import static okjava.util.check.Once.calledOnce;

import okjava.util.annotation.Utility;
import okjava.util.check.Dummy;
import okjava.util.check.Never;

import java.util.function.Consumer;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 9/17/2016
 * 14:40.
 */
@Utility
public enum EmptyConsumer {
    ;

    private static ConsumerImpl<?> INSTANCE = new ConsumerImpl<>(Dummy.create());

    EmptyConsumer(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    @SuppressWarnings("unchecked") // safe as implementation is empty.
    public static <T> Consumer<T> create() {
        return (Consumer<T>) INSTANCE;
    }

    public static <T> Consumer<T> emptyConsumer() {
        return create();
    }

    public static <T> Consumer<T> empty() {
        return create();
    }

    private static final class ConsumerImpl<T> implements Consumer<T> {

        private ConsumerImpl(Dummy dummy) {
            calledOnce(ConsumerImpl.class);
        }

        @Override
        public void accept(T t) {
        }

        @Override
        public String toString() {
            return "EmptyConsumer";
        }
    }
}
