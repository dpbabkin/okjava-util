package okjava.util.classcast;

import static okjava.util.NotNull.notNull;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/16/2018
 * 20:23.
 */
public class CasterFunction<B, T extends B> implements Function<B, T> {

    private final Class<T> clazz;
    private final Consumer<B> rejectHandler;

    private CasterFunction(Class<T> clazz, Consumer<B> rejectHandler) {
        this.clazz = notNull(clazz);
        this.rejectHandler = notNull(rejectHandler);
    }

    public static <B, T extends B> CasterFunction<B, T> caster(Class<T> clazz, Consumer<B> rejectHandler) {
        return create(clazz, rejectHandler);
    }

    public static <B, T extends B> CasterFunction<B, T> create(Class<T> clazz, Consumer<B> rejectHandler) {
        return new CasterFunction<>(clazz, rejectHandler);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T apply(B object) {
        if (object.getClass().isAssignableFrom(clazz)) {
            return (T) object;
        } else {
            rejectHandler.accept(object);
        }
        throw new RuntimeException("object = " + object);
    }
}
