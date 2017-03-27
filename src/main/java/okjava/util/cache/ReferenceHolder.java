package okjava.util.cache;


import static okjava.util.NotNull.notNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.Reference;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(ReferenceHolder.class);

    private Reference<Wrapper> reference = null;

    private final Supplier<T> valueFactory;

    private Function<Wrapper, Reference<Wrapper>> referenceFactory;

    public static <X> ReferenceHolder<X> createSoft(Supplier<X> valueFactory) {
        return new ReferenceHolder<X>(valueFactory, ReferenceHolder::createSoftRef);
    }

    public static <X> ReferenceHolder<X> createWeak(Supplier<X> valueFactory) {
        return new ReferenceHolder<X>(valueFactory, ReferenceHolder::createWeakRef);
    }

    private static <X> Reference<X> createSoftRef(X x) {
        return new SoftReference<>(x);
    }

    private static <X> Reference<X> createWeakRef(X x) {
        return new WeakReference<>(x);
    }

    private static <T> ReferenceHolder<T> create(Supplier<T> valueFactory, Function<ReferenceHolder<T>.Wrapper, Reference<ReferenceHolder<T>.Wrapper>> referenceFactory) {
        return new ReferenceHolder<T>(valueFactory, referenceFactory);
    }


    private ReferenceHolder(Supplier<T> valueFactory, Function<ReferenceHolder<T>.Wrapper, Reference<ReferenceHolder<T>.Wrapper>> referenceFactory) {
        this.valueFactory = notNull(valueFactory);
        this.referenceFactory = notNull(referenceFactory);
    }

    @Override
    public T get() {
        Reference<ReferenceHolder<T>.Wrapper> reference = this.reference;
        if (reference == null) {
            reference = createReference();
            this.reference = reference;
        }

        ReferenceHolder<T>.Wrapper wrapper = reference.get();
        if (wrapper == null) {
            T value = valueFactory.get();
            this.reference = createReference(value);
            return value;
        }
        return wrapper.get();
    }

    private Reference<ReferenceHolder<T>.Wrapper> createReference() {
        return createReference(valueFactory.get());
    }

    private Reference<ReferenceHolder<T>.Wrapper> createReference(T value) {
        return referenceFactory.apply(new ReferenceHolder<T>.Wrapper(value));
    }

    private class Wrapper implements Supplier<T> {
        private final T value;

        private Wrapper(T value) {
            this.value = notNull(value);
            LOGGER.trace("created " + this);
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            reference = null;
            LOGGER.trace("finalized " + this);
        }

        @Override
        public T get() {
            return value;
        }

        @Override
        public String toString() {
            return "ReferenceHolder.Wrapper{value=" + value + "}";
        }
    }
}
