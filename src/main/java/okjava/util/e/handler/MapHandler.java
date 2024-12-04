package okjava.util.e.handler;

import okjava.util.e.handler.atomic.ThrowableHandler;

import java.util.Optional;

@Deprecated // do not see any usages of this class.
public interface MapHandler {

    default <T extends Throwable> ThrowableHandler<T> getHandler(Class<? extends T> clazz) {
        return getHandler(clazz, c -> {
            throw new IllegalArgumentException(clazz.getName());
        });
    }

    default <T extends Throwable> ThrowableHandler<T> getHandler(Class<? extends T> clazz, ThrowableHandler<T> defaultValue) {
        Optional<ThrowableHandler<T>> optional = getHandlerAsOptional(clazz);
        return optional.orElse(defaultValue);
    }

    <T extends Throwable> Optional<ThrowableHandler<T>> getHandlerAsOptional(Class<? extends T> clazz);
}
