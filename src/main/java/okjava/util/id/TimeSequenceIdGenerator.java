package okjava.util.id;

import static okjava.util.check.Once.calledOnce;
import static okjava.util.id.timesequence.TimeSequenceIdFactory.timeSequenceIdFactory;

import okjava.util.annotation.Singleton;
import okjava.util.id.timesequence.TimeSequenceId;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 20:25.
 */
@Singleton
@Deprecated // use long Generator instead. this one doesn't update counter.
final class TimeSequenceIdGenerator implements IdGenerator<TimeSequenceId> {

    private static IdGenerator<TimeSequenceId> INSTANCE = new TimeSequenceIdGenerator();
    private final IdGenerator<Long> idGenerator = AtomicIdGenerator.atomicIdGenerator();

    private TimeSequenceIdGenerator() {
        calledOnce(this.getClass());
    }

    static IdGenerator<TimeSequenceId> i() {
        return INSTANCE;
    }

    static IdGenerator<TimeSequenceId> timeSequenceIdGenerator() {
        return INSTANCE;
    }

    static IdGenerator<TimeSequenceId> create() {
        return INSTANCE;
    }

    private static long millis() {
        return System.currentTimeMillis();
    }

    public TimeSequenceId generate() {
        int hashCode = Thread.currentThread().hashCode();
        if ((hashCode & 1) == 1) {
            return timeSequenceIdFactory().create(millis(), idGenerator.generate());
        } else {
            long id = idGenerator.generate();
            return timeSequenceIdFactory().create(millis(), id);
        }
    }
}
