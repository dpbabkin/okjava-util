package okjava.util.condition;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;

import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/16/2019
 * 18:19.
 */
public class WaitForCollection<E, C extends Collection<E>> {

    private final C collection;

    private final WaiterFactory block = WaiterFactories.create();
    private final CollectionWaiters<E, C> collectionWaiters = tester -> block.waiterBoolean(() -> tester.test(WaitForCollection.this.collection));

    private WaitForCollection(C collection) {
        this.collection = notNull(collection);
    }

    public static <E> WaitForCollection<E, List<E>> createWithArrayList() {
        return new WaitForCollection<>(Lists.newArrayList());
    }

    public static <E> WaitForCollection<E, Queue<E>> createWithConcurrentLinkedQueue() {
        return new WaitForCollection<>(Queues.newConcurrentLinkedQueue());
    }

    public static <E> WaitForCollection<E, Set<E>> createWithHashSet() {
        return new WaitForCollection<>(Sets.newHashSet());
    }

    public static <E, C extends Collection<E>> WaitForCollection<E, C> create(C collection) {
        return new WaitForCollection<>(collection);
    }

    public boolean add(E element) {
        synchronized (block) { //need to avoid size value skipped.
            try {
                return collection.add(element);
            } finally {
                block.getUpdatable().onUpdate();
            }
        }
    }

    public C getCollection() {
        return collection;
    }

    public CollectionWaiters<E, C> getCollectionWaiters() {
        return collectionWaiters;
    }
}
