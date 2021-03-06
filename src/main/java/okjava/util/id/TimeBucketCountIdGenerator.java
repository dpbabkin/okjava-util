package okjava.util.id;

import okjava.util.annotation.Singleton;
import okjava.util.id.timesequence.TimeSequenceId;

import java.util.concurrent.locks.ReentrantLock;

import static okjava.util.check.Once.calledOnce;
import static okjava.util.id.timesequence.TimeSequenceIdFactory.timeSequenceIdFactory;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 20:25.
 */
@Singleton
@Deprecated // never used.
final class TimeBucketCountIdGenerator implements IdGenerator<TimeSequenceId> {
    private static IdGenerator<TimeSequenceId> INSTANCE = new TimeBucketCountIdGenerator();
    private final ReentrantLock lock = new ReentrantLock();
    private long time = 0;
    private long sequence = 0;

    private TimeBucketCountIdGenerator() {
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

        int tryCount = 0;
        long t;
        long sequence;
        for (; ; ) {
            final long millis = millis();
            if (doLock(tryCount)) {
                try {
                    if (this.time < millis) {
                        assert millis > this.time;
                        this.sequence = 1;
                        sequence = 0;
                        this.time = millis;
                    } else {
                        sequence = this.sequence++;
                    }
                    t = this.time;
                    break;
                } finally {
                    lock.unlock();
                }
            }
            tryCount++;
        }

        return timeSequenceIdFactory().create(t, sequence);
    }

    private boolean doLock(int tryCount) {
        if (tryCount > 10) {
            lock.lock();
            return true;
        }
        return lock.tryLock();
    }
}
