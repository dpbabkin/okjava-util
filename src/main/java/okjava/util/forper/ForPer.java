package okjava.util.forper;

import java.util.function.Function;

/**
 * Name ForPer stands for Formatter+Parser
 *
 * @param <V>
 */
public interface ForPer<V> {
    Function<String, V> getParser();

    Function<V, String> getFormatter();

    default String format(V value) {
        return getFormatter().apply(value);
    }

    default V parse(String string) {
        return getParser().apply(string);
    }
}
