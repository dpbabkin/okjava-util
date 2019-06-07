package okjava.util.condition;

import static okjava.util.NotNull.notNull;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/16/2019
 * 18:19.
 */
public class WaitForCollection<E, C extends Collection<E>> implements Consumer<E> {

    private final C collection;

    private final BlockingWaitForEvent block = BlockingWaitForEvent.create();

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
        try {
            return collection.add(element);
        } finally {
            block.update();
        }
    }

    public C getCollection() {
        return collection;
    }

    public Waiter createWaiter(int number) {
        return block.waiter(() -> collection.size() >= number);
    }

    @Override
    public void accept(E e) {
        add(e);
    }
}
