package okjava.util.id;

import static okjava.util.check.Once.calledOnce;

import okjava.util.annotation.Singleton;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 20:23.
 */
@Singleton
public class AtomicIdGenerator implements IdGenerator<Long> {

    private final static IdGenerator<Long> INSTANCE = new AtomicIdGenerator();
    private final AtomicLong atomicLong = new AtomicLong(0);

    private AtomicIdGenerator() {
        calledOnce(this.getClass());
    }

    public static IdGenerator<Long> i() {
        return INSTANCE;
    }

    public static IdGenerator<Long> create() {
        return INSTANCE;
    }

    public static IdGenerator<Long> atomicIdGenerator() {
        return INSTANCE;
    }

    @Override
    public Long generate() {
        return atomicLong.getAndIncrement();
    }
}
