package okjava.util.id;

import static okjava.util.check.Once.calledOnce;
import static okjava.util.id.timesequence.LongTimeSequenceIdUtils.incrementSequence;
import static okjava.util.id.timesequence.LongTimeSequenceIdUtils.joinTimeAndSequence;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 20:25.
 */
class LongTimeTimeSequenceIdGenerator implements IdGenerator<Long> {


    private static IdGenerator<Long> INSTANCE = new LongTimeTimeSequenceIdGenerator();
    private final AtomicLong time = new AtomicLong(joinTimeAndSequence(millis(), 0));

    private LongTimeTimeSequenceIdGenerator() {
        calledOnce(this.getClass());
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    static IdGenerator<Long> i() {
        return INSTANCE;
    }

    static IdGenerator<Long> timeSequenceIdGenerator() {
        return INSTANCE;
    }

    static IdGenerator<Long> create() {
        return INSTANCE;
    }

    private static long millis() {
        return System.currentTimeMillis();
    }


    public Long generate() {
        long newValue;
        for (; ; ) {
            final long millis = millis();
            newValue = joinTimeAndSequence(millis, 0);
            final long oldValue = time.get();
            if (newValue <= oldValue) {
                newValue = incrementSequence(oldValue);
            }
            if (this.time.compareAndSet(oldValue, newValue)) {
                break;
            }
        }
        return newValue;
    }
}
