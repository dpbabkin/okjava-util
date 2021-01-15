package okjava.util.cache;


import okjava.util.annotation.Singleton;

import java.util.function.Supplier;

import static okjava.util.check.Once.calledOnce;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 3/27/2017
 * 19:45.
 */

@Singleton
public final class ReferenceHolderFactory {

    private static final ReferenceHolderFactory INSTANCE = new ReferenceHolderFactory();

    private ReferenceHolderFactory() {
        calledOnce(this.getClass());
    }

    public static ReferenceHolderFactory getInstance() {
        return INSTANCE;
    }

    public static <X> Supplier<X> createSoft(Supplier<X> valueFactory) {
        return new SoftReferenceHolder<>(valueFactory);
    }

    public static <X> Supplier<X> createWeak(Supplier<X> valueFactory) {
        return new WeakReferenceHolder<>(valueFactory);
    }

}
