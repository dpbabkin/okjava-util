package okjava.util.testutils;

import static okjava.util.NotNull.notNull;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/16/2019
 * 18:19.
 */
public class WaitForCollection<E, C extends Collection<E>> implements Consumer<E> {

    private final C collection;
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    private WaitForCollection(C collection) {
        this.collection = notNull(collection);
    }

    public static <E> WaitForCollection<E, List<E>> createWithArrayList() {
        return new WaitForCollection<>(Lists.newArrayList());
    }

    public static <E, C extends Collection<E>> WaitForCollection<E, C> create(C collection) {
        return new WaitForCollection<>(collection);
    }

    public boolean add(E element) {
        lock.lock();
        try {
            condition.signalAll();
            return collection.add(element);
        } finally {
            lock.unlock();
        }
    }

    public C getCollection() {
        return collection;
    }

    public void awaitAppearing(int number) throws InterruptedException {
        assert number >= 0 : number;
        for (; ; ) {
            lock.lock();
            try {
                if (collection.size() >= number) {
                    return;
                }
                condition.await();
            } finally {
                lock.unlock();
            }
        }
    }

    public boolean awaitAppearing(int number, long time, TimeUnit timeUnit) throws InterruptedException {
        assert number >= 0 : number;
        final long beginTime = System.currentTimeMillis();
        final long requestedWaitTime = timeUnit.toMillis(time);
        for (; ; ) {
            lock.lock();
            try {
                if (collection.size() >= number) {
                    return true;
                }

                long timeElapsed = (System.currentTimeMillis() - beginTime);
                long needToWait = requestedWaitTime - timeElapsed;
                if (needToWait <= 0 || condition.await(needToWait, TimeUnit.MILLISECONDS) == false) {
                    return false;
                }
            } finally {
                lock.unlock();
            }
        }
    }

    @Override
    public void accept(E e) {
        add(e);
    }
}
