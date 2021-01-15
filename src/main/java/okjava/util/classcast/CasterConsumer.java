package okjava.util.classcast;

import java.util.function.Consumer;

import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/16/2018
 * 20:23.
 */
public class CasterConsumer<B, T extends B> implements Consumer<B> {

    private final Class<T> clazz;
    private final Consumer<T> consumer;
    private final Consumer<B> rejectHandler;

    private CasterConsumer(Class<T> clazz, Consumer<T> consumer, Consumer<B> rejectHandler) {
        this.clazz = notNull(clazz);
        this.consumer = notNull(consumer);
        this.rejectHandler = notNull(rejectHandler);
    }

    public static <B, T extends B> CasterConsumer<B, T> caster(Class<T> clazz, Consumer<T> consumer, Consumer<B> rejectHandler) {
        return create(clazz, consumer, rejectHandler);
    }

    public static <B, T extends B> CasterConsumer<B, T> create(Class<T> clazz, Consumer<T> consumer, Consumer<B> rejectHandler) {
        return new CasterConsumer<>(clazz, consumer, rejectHandler);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void accept(B object) {
        if (object.getClass().isAssignableFrom(clazz)) {
            consumer.accept((T) object);
        } else {
            rejectHandler.accept(object);
        }
    }
}
