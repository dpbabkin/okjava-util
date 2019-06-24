package okjava.util.e.handler;

import com.google.common.collect.ImmutableSortedMap;
import okjava.util.e.handler.atomic.ThrowableHandler;

import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;

class MapHandlerImpl implements MapHandler {

    private final SortedMap<Class<? extends Throwable>, ThrowableHandler<? extends Throwable>> exceptionHandlers;

    static MapHandlerImpl create(SortedMap<Class<? extends Throwable>, ThrowableHandler<? extends Throwable>> handlers) {
        return new MapHandlerImpl(handlers);
    }

    private MapHandlerImpl(SortedMap<Class<? extends Throwable>, ThrowableHandler<? extends Throwable>> handlers) {
        this.exceptionHandlers = ImmutableSortedMap.copyOfSorted(handlers);
    }

    @Override
    public <T extends Throwable> Optional<ThrowableHandler<T>> getHandlerAsOptional(Class<? extends T> clazz) {
        Optional<ThrowableHandler<T>> optional = exceptionHandlers.entrySet()
                .stream()
                .filter(e -> e.getKey().isAssignableFrom(clazz))
                .map(Map.Entry::getValue)
                .map(v -> (ThrowableHandler<T>) v)
                .findFirst();
        return optional;
    }

}
