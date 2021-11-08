package okjava.util.fopar;

import java.util.function.Function;

import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/9/2019
 * 18:05.
 */

public abstract class ABFoPaR<V> implements FoPaR<V> {

    private final Function<String, V> parser;
    private final Function<V, String> formatter;

    protected ABFoPaR(Function<String, V> parser, Function<V, String> formatter) {
        this.parser = notNull(parser);
        this.formatter = notNull(formatter);
    }

    @Override
    public Function<String, V> getParser() {
        return parser;
    }

    @Override
    public Function<V, String> getFormatter() {
        return formatter;
    }
}
