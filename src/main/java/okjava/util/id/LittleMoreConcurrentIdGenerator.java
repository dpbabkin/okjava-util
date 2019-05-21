package okjava.util.id;

import static okjava.util.check.MathCheck.lessThenOrEqual;

import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/10/2018
 * 08:33.
 */
@Deprecated // looks like this was raw prototype.
class LittleMoreConcurrentIdGenerator implements Supplier<Long> {

    public final static long NOT_ACQUIRE_VALUE = -1;

    private final static int DEFAULT_CONCURRENT_SLOTS = 2;

    private final Slot[] array;

    //private final AtomicLong atomicLong = new AtomicLong(0);

    private LittleMoreConcurrentIdGenerator(int concurrentSlots) {
        lessThenOrEqual(2, concurrentSlots);
        this.array = IntStream.range(0, concurrentSlots)
                         .mapToObj(i -> new Slot())
                         .toArray(Slot[]::new);
    }

    public static Supplier<Long> create() {
        return idGenerator();
    }

    public static Supplier<Long> create(int concurrentSlots) {
        return idGenerator(concurrentSlots);
    }

    public static Supplier<Long> idGenerator() {
        return idGenerator(DEFAULT_CONCURRENT_SLOTS);
    }

    public static Supplier<Long> idGenerator(int concurrentSlots) {
        return new LittleMoreConcurrentIdGenerator(concurrentSlots);
    }

    int getSlotSize() {
        return array.length;
    }

    @Override
    public Long get() {
        for (int i = 0; i < array.length; i++) {
            Slot slot = array[i];
            long value = slot.increment();
            if (value != NOT_ACQUIRE_VALUE) {
                return (value * array.length) + i;
            }
        }
        return NOT_ACQUIRE_VALUE;
    }

    private final class Slot {
        private final ReentrantLock lock = new ReentrantLock();
        private long counter = 0;

        long increment() {
            ReentrantLock lock = this.lock;
            if (lock.tryLock()) {
                try {
                    return counter++;
                } finally {
                    lock.unlock();
                }
            }
            return NOT_ACQUIRE_VALUE;
        }
    }
}
