package okjava.util.clazz;

import okjava.util.logger.LoggerUtils;
import okjava.util.string.ToStringBuffer;
import org.slf4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

import static okjava.util.NotEmpty.notEmptyString;
import static okjava.util.NotNull.notNull;


public class ClassResolver<T> {

    private static final Logger LOGGER = LoggerUtils.createLogger(ClassResolver.class);

    private final String fullClassName;
    private final Supplier<T> defaultProvider;
    private final Class<T> clazz;

    public static <T> ClassResolver<T> create(String fullClassName, Supplier<T> defaultProvider, Class<T> clazz) {
        return new ClassResolver<>(fullClassName, defaultProvider, clazz);
    }

    public static <T> T resolve(String fullClassName, Supplier<T> defaultProvider, Class<T> clazz) {
        try {
            Class<?> resolvedClazz = Class.forName(fullClassName);
            ToStringBuffer.string("class resolved")
                    .add("fullClassName", fullClassName)
                    .add("resolvedClazz", resolvedClazz)
                    .toDebug(LOGGER);
            if (clazz.isAssignableFrom(resolvedClazz)) {
                Object object = resolvedClazz.getMethod("create").invoke(null);
                T result = clazz.cast(object);
                ToStringBuffer.string("object instance resolved")
                        .add("object", result)
                        .add("fullClassName", fullClassName)
                        .add("resolvedClazz", resolvedClazz)
                        .toDebug(LOGGER);
                return result;
            }
            throw ToStringBuffer.string("can not cast class")
                    .add("from resolvedClazz", resolvedClazz)
                    .add("to clazz", clazz)
                    .toException(ClassCastException::new);
        } catch (ClassNotFoundException e) {
            // ignore
            LOGGER.atTrace().log(ToStringBuffer.string("PoolFactory instance not found").add("fullClassName", fullClassName).toString());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassCastException e) {
            ToStringBuffer.string("Can not instantiate create.")
                    .add("fullClassName", fullClassName)
                    .addThrowable(e)
                    .toError(LOGGER, e)
                    .assertFalse();
        }
        return defaultProvider.get();
    }

    private ClassResolver(String fullClassName, Supplier<T> defaultProvider, Class<T> clazz) {
        this.fullClassName = notEmptyString(fullClassName);
        this.defaultProvider = notNull(defaultProvider);
        this.clazz = notNull(clazz);
    }

    public T resolve() {
        return resolve(this.fullClassName, this.defaultProvider, this.clazz);
    }

    @Override
    public String toString() {
        return ToStringBuffer.of(this)
                .add("fullClassName", this.fullClassName)
                .add("clazz", this.clazz)
                .add("defaultProvider", this.defaultProvider)
                .toString();
    }
}
