package okjava.util;

import java.util.function.Consumer;

public class ConsumerHolderOnce<T> implements Consumer<T> { //}, Supplier<Consumer<T>> {

    private final HolderOnce<Consumer<T>> holderOnce = HolderOnce.empty();

    @Override
    public void accept(T t) {
        holderOnce.get().accept(t);
    }

    //    @Override
    public Consumer<T> get() {
        return holderOnce.get();
    }

    public void set(Consumer<T> consumer) {
        holderOnce.setValue(consumer);
    }
}
