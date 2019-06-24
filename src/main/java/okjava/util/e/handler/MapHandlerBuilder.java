package okjava.util.e.handler;

import com.google.common.collect.Maps;
import okjava.util.e.handler.atomic.ThrowableHandler;

import java.util.Comparator;
import java.util.SortedMap;

public class MapHandlerBuilder {

    private static final Comparator<Class<? extends Throwable>> classComparator = (clazz1, clazz2) -> {
        if (clazz1.isAssignableFrom(clazz2)) {
            return 1;
        }
        if (clazz2.isAssignableFrom(clazz1)) {
            return -1;
        }
        return clazz1.getName().compareTo(clazz2.getName());
    };

    private final SortedMap<Class<? extends Throwable>, ThrowableHandler<? extends Throwable>> handlers = Maps.newTreeMap(classComparator);

    private MapHandlerBuilder() {
    }

    public static MapHandlerBuilder builder() {
        return new MapHandlerBuilder();
    }

    public MapHandlerImpl build() {
        return MapHandlerImpl.create(handlers);
    }

    public <C extends Throwable> MapHandlerBuilder addHandler(Class<C> clazz, ThrowableHandler<C> handler) {
        handlers.put(clazz, handler);
        return this;
    }
}
