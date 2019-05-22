package okjava.util.cache;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/18/2017
 * 22:11.
 */
class SoftReferenceHolder<T> extends ReferenceHolder<T> {

    SoftReferenceHolder(Supplier<T> valueFactory) {
        super(valueFactory);
    }

    @Override
    protected Function<T, Reference<T>> getReferenceFactory() {
        return x -> new SoftReference<>(x, ReferenceHolderFinalizer.instance().getReferenceQueue());
    }
}
