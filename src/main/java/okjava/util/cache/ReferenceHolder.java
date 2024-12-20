package okjava.util.cache;


import java.lang.ref.Reference;
import java.util.function.Function;
import java.util.function.Supplier;

import static okjava.util.NotNull.notNull;
import static okjava.util.thread.RunnableUtils.wrapToString;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 3/27/2017
 * 19:45.
 */
abstract class ReferenceHolder<T> implements Supplier<T> {

    private final Supplier<T> valueFactory;
    private Reference<T> reference = null;

    ReferenceHolder(Supplier<T> valueFactory) {
        this.valueFactory = notNull(valueFactory);
    }

    protected abstract Function<T, Reference<T>> getReferenceFactory();


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
        Reference<T> reference = getReferenceFactory().apply(value);
        ReferenceHolderFinalizer.<T>instance().registerReference(reference,
                wrapToString(this::clean, () -> ReferenceHolder.this.getClass().getSimpleName() + ":" + valueFactory.toString()));
        return reference;
    }

    private void clean() {
        this.reference = null;
    }
}
