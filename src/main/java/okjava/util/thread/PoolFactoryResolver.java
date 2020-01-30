package okjava.util.thread;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;
import okjava.util.logger.LoggerUtils;
import okjava.util.string.ToStringBuffer;
import org.slf4j.Logger;

import java.lang.reflect.InvocationTargetException;

import static okjava.util.check.Never.neverNeverCalled;

@Utility
enum PoolFactoryResolver {
    ;

    PoolFactoryResolver(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    private static final Logger LOGGER = LoggerUtils.createLogger(PoolFactoryResolver.class);

    private static final String POOL_FACTORY_CLASS_NAME = "okjava.util.thread.PoolFactoryInstance";

    static PoolFactory resolvePollFactory() {
        return resolvePollFactory(POOL_FACTORY_CLASS_NAME);
    }

    private static PoolFactory resolvePollFactory(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            return (PoolFactory) clazz.getMethod("create").invoke(null);
        } catch (ClassNotFoundException e) {
            // ignore
            LOGGER.atTrace().log(ToStringBuffer.string("PoolFactory instance not found").add("className", className).toString());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.error(ToStringBuffer.string("Can not create PoolFactory")
                    .add("className", className)
                    .addThrowable(e)
                    .toString());
        }
        return PoolFactoryImpl.create();
    }
}
