package okjava.util.condition;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/16/2019
 * 18:19.
 */
public class WaitForCollection<E, C extends Collection<E>> implements Consumer<E> {

    private final C collection;

    private final WaiterFactory block = WaiterFactories.create();

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

    public Waiter<Result> createSizeMoreOrEqualWaiter(int size) {
        return block.waiterBoolean(() -> collection.size() >= size);
    }

    public Waiter<Result> createSizeEqualWaiter(int size) {
        return block.waiterBoolean(() -> collection.size() == size);
    }

    public Waiter<Result> createSizeMoreWaiter(int size) {
        return block.waiterBoolean(() -> collection.size() > size);
    }

    public Waiter<Result> createWaiter(Function<C, Boolean> tester) {
        return block.waiterBoolean(() -> tester.apply(collection));
    }

    @Override
    public void accept(E e) {
        add(e);
    }
}
