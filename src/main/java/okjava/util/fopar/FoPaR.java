package okjava.util.fopar;

import java.util.function.Function;

/**
 * Name FoPar stands for Formatter+Parser
 *
 * @param <V>
 */
public interface FoPaR<V> {
    Function<String, V> getParser();

    Function<V, String> getFormatter();

    default String format(V value) {
        return getFormatter().apply(value);
    }

    default V parse(String string) {
        return getParser().apply(string);
    }
}
