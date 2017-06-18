package okjava.util.cache;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         6/18/2017
 *         22:11.
 */
class WeakReferenceHolder<T> extends ReferenceHolder<T> {

    WeakReferenceHolder(Supplier<T> valueFactory) {
        super(valueFactory);
    }

    @Override
    protected Function<T, Reference<T>> getReferenceFactory() {
        return x -> new WeakReference<>(x, ReferenceHolderFinalizer.instance().getReferenceQueue());
    }
}
