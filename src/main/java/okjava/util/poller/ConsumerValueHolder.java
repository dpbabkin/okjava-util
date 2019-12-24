package okjava.util.poller;

import java.util.function.Consumer;

public interface ConsumerValueHolder<V> extends ValueHolder<V>, Consumer<V> {
}
