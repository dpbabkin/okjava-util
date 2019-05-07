package okjava.util.id;

import static okjava.util.check.Once.calledOnce;
import static okjava.util.id.timesequence.TimeSequenceIdFactory.timeSequenceIdFactory;

import okjava.util.annotation.Singleton;
import okjava.util.id.timesequence.TimeSequenceId;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 20:25.
 */
@Singleton
class TimeBucketCountIdGenerator implements IdGenerator<TimeSequenceId> {
    private final ReentrantLock lock = new ReentrantLock();
    private long time = 0;
    private long sequence = 0;

    private static IdGenerator<TimeSequenceId> INSTANCE = new TimeBucketCountIdGenerator();

    static IdGenerator<TimeSequenceId> i() {
        return INSTANCE;
    }

    static IdGenerator<TimeSequenceId> timeSequenceIdGenerator() {
        return INSTANCE;
    }

    static IdGenerator<TimeSequenceId> create() {
        return INSTANCE;
    }

    private TimeBucketCountIdGenerator() {
        calledOnce(this.getClass());
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
                    t=this.time;
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

    private boolean tryLock() {
        return lock.tryLock();
    }

    private boolean lock() {
        lock.lock();
        return true;
    }
}
