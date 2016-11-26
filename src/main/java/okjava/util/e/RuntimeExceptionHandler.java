package okjava.util.e;

import java.util.function.Consumer;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         11/26/2016
 *         11:06.
 */
@FunctionalInterface
public interface RuntimeExceptionHandler extends ExceptionHandler<RuntimeException> {

    static RuntimeExceptionHandler fromConsumer(Consumer<RuntimeException> runtimeExceptionConsumer) {
        return runtimeExceptionConsumer::accept;
    }
}
