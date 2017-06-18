package okjava.util.cache;


import static okjava.util.NotNull.notNull;
import static okjava.util.RunnableUtils.wrapToString;

import okjava.util.RunnableUtils;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         3/27/2017
 *         19:45.
 */
public class ReferenceHolder<T> implements Supplier<T> {

    private Reference<T> reference = null;

    private final Supplier<T> valueFactory;

    private Function<T, Reference<T>> referenceFactory;

    public static <X> ReferenceHolder<X> createSoft(Supplier<X> valueFactory) {
        return new ReferenceHolder<X>(valueFactory, x -> createSoftRef(x, ReferenceHolderFinalizer.instance().getReferenceQueue()));
    }

    public static <X> ReferenceHolder<X> createWeak(Supplier<X> valueFactory) {
        return new ReferenceHolder<X>(valueFactory, x -> createWeakRef(x, ReferenceHolderFinalizer.instance().getReferenceQueue()));
    }

    private static <X> Reference<X> createSoftRef(X x, ReferenceQueue<? super X> queue) {
        return new SoftReference<>(x, queue);
    }

    private static <X> Reference<X> createWeakRef(X x, ReferenceQueue<? super X> queue) {
        return new WeakReference<>(x, queue);
    }

    private static <T> ReferenceHolder<T> create(Supplier<T> valueFactory, Function<T, Reference<T>> referenceFactory) {
        return new ReferenceHolder<T>(valueFactory, referenceFactory);
    }


    private ReferenceHolder(Supplier<T> valueFactory, Function<T, Reference<T>> referenceFactory) {
        this.valueFactory = notNull(valueFactory);
        this.referenceFactory = notNull(referenceFactory);
    }

    @Override
    public T get() {
        Reference<T> reference = this.reference;
        T value = null;
        if (reference != null) {
            value = reference.get();
        }

        if (value == null) {
            value = valueFactory.get();
            this.reference = createReference(value);
        }
        return value;
    }

    private Reference<T> createReference(T value) {
        Reference<T> reference = referenceFactory.apply(value);
        ReferenceHolderFinalizer.<T>instance().registerReference(reference,
            wrapToString(this::clean, () -> ReferenceHolder.this.getClass().getSimpleName() + ":" + valueFactory.toString()));
        return reference;
    }

    private void clean() {
        this.reference = null;
    }
}
