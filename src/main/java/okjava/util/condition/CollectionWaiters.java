package okjava.util.condition;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 05.02.2022 - 15:56.
 */
public interface CollectionWaiters<E, C extends Collection<E>> extends Pollable<CollectionWaiters<E, C>> {

    Waiter<Result> createWaiter(Predicate<C> tester);

    default Waiter<Result> createSizeMoreOrEqualWaiter(int size) {
        return createWaiter(c -> c.size() > size);
    }

    default Waiter<Result> createSizeEqualWaiter(int size) {
        return createWaiter(c -> c.size() == size);
    }

    default Waiter<Result> createSizeMoreWaiter(int size) {
        return createWaiter(c -> c.size() > size);
    }

    default Waiter<Result> createNotEmptyWaiter() {
        return createWaiter(c -> !c.isEmpty());
    }
}
